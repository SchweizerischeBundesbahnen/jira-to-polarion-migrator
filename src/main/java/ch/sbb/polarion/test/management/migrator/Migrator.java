package ch.sbb.polarion.test.management.migrator;

import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import ch.sbb.polarion.test.management.migrator.connector.polarion.PolarionConnector;
import ch.sbb.polarion.test.management.migrator.model.jira.JiraIssues;
import ch.sbb.polarion.test.management.migrator.model.polarion.WorkItem;
import ch.sbb.polarion.test.management.migrator.model.polarion.WorkItems;
import ch.sbb.polarion.test.management.migrator.utils.JiraIssueUtils;
import ch.sbb.polarion.test.management.migrator.utils.MappingFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.VisibleForTesting;

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

        List<JiraIssues> jiraIssuesList = JiraIssueUtils.getJiraIssues(migratorConfig);

        log.info("Starting creation of issues in Polarion...");

        Map<String, String> mapping = processJiraIssues(jiraIssuesList, new PolarionConnector(migratorConfig));

        MappingFileUtils.saveMappingToFile(mapping, migratorConfig);
    }

    @VisibleForTesting
    static Map<String, String> processJiraIssues(List<JiraIssues> jiraIssuesList, PolarionConnector connector) {
        Map<String, String> mapping = new HashMap<>();

        for (JiraIssues jiraIssues : jiraIssuesList) {
            if (jiraIssues.issues != null) {
                if (jiraIssues.issues.isEmpty()) {
                    log.info("There is no jira issues for one more iteration...");
                    break;
                }
                log.info("Number of obtained issues: {}", jiraIssues.issues.size());

                WorkItems workItems = new WorkItems();
                workItems.fromJiraIssues(jiraIssues.issues, Migrator.migratorConfig);
                log.info("Jira issues were successfully mapped to Polarion Work Items");

                List<WorkItem> importedWorkItems = connector
                        .importWorkItems(workItems)
                        .getData();

                log.info("{} Jira Issues were migrated to Polarion", importedWorkItems.size());

                for (int i = 0; i < importedWorkItems.size(); i++) {
                    String jiraIssueKey = jiraIssues.issues.get(i).key;
                    String workItemId = importedWorkItems.get(i).getId();
                    mapping.put(jiraIssueKey, workItemId);
                }
            } else {
                log.error("Error querying issues");
            }
        }

        return mapping;
    }
}
