package ch.sbb.polarion.test.management.migrator;

import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import ch.sbb.polarion.test.management.migrator.connector.polarion.PolarionConnector;
import ch.sbb.polarion.test.management.migrator.model.jira.JiraIssues;
import ch.sbb.polarion.test.management.migrator.model.polarion.WorkItem;
import ch.sbb.polarion.test.management.migrator.model.polarion.WorkItems;
import ch.sbb.polarion.test.management.migrator.utils.JiraIssueUtils;
import ch.sbb.polarion.test.management.migrator.utils.MappingFileUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Migrator {
    public static final MigratorConfig migratorConfig = new MigratorConfig();

    public static void main(String[] args) throws IOException {
        migratorConfig.loadConfig();

        MappingFileUtils.deleteMappingFile(migratorConfig);

        Map<String, String> mapping = new HashMap<>();
        List<JiraIssues> jiraIssuesList = JiraIssueUtils.getJiraIssues(migratorConfig);

        log.info("Starting creation of issues in Polarion...");

        for (JiraIssues jiraIssues : jiraIssuesList) {
            if (jiraIssues.issues == null) {
                log.error("Error querying issues");
                continue;
            }

            if (jiraIssues.issues.isEmpty()) {
                log.info("No Jira issues left in this iteration");
                break;
            }

            log.info("Number of issues: {}", jiraIssues.issues.size());

            WorkItems workItems = new WorkItems();
            workItems.fromJiraIssues(jiraIssues.issues, migratorConfig);

            List<WorkItem> importedItems = new PolarionConnector(migratorConfig)
                    .importWorkItems(workItems)
                    .getData();

            for (int i = 0; i < importedItems.size(); i++) {
                mapping.put(jiraIssues.issues.get(i).key, importedItems.get(i).getId());
            }
        }

        MappingFileUtils.saveMappingToFile(mapping, migratorConfig);
    }
}
