package ch.sbb.polarion.test.management.migrator;

import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import ch.sbb.polarion.test.management.migrator.connector.jira.JiraConnector;
import ch.sbb.polarion.test.management.migrator.connector.jira.jql.JqlBuilder;
import ch.sbb.polarion.test.management.migrator.connector.polarion.PolarionConnector;
import ch.sbb.polarion.test.management.migrator.model.jira.JiraIssues;
import ch.sbb.polarion.test.management.migrator.model.polarion.WorkItem;
import ch.sbb.polarion.test.management.migrator.model.polarion.WorkItems;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Migrator {

    public static final String JIRA_ISSUES_FROM_FILE = "jira.issues.from.file";
    public static final String JIRA_TO_POLARION_MAPPING = "jira-to-polarion.mapping";

    public static void main(String[] args) throws IOException {
        MigratorConfig.INSTANCE.loadConfig();

        deleteMappingFile();
        Map<String, String> jiraIssueToPolarionWorkItemMapping = new HashMap<>();

        List<JiraIssues> jiraIssuesList = getJiraIssues();

        log.info("Starting creation issues in Polarion...");

        for (JiraIssues jiraIssues : jiraIssuesList) {
            if (jiraIssues.issues != null) {
                if (jiraIssues.issues.isEmpty()) {
                    log.info("There is no jira issues for one more iteration...");
                    break;
                }
                log.info("Number of obtained issues: {}", jiraIssues.issues.size());

                WorkItems workItems = new WorkItems();
                workItems.fromJiraIssues(jiraIssues.issues);
                log.info("Jira issues were successfully mapped to Polarion Work Items");

                List<WorkItem> importedWorkItems = new PolarionConnector().importWorkItems(workItems).getData();
                log.info("{} Jira Issues were migrated to Polarion", importedWorkItems.size());

                for (int i = 0; i < importedWorkItems.size(); i++) {
                    String jiraIssueKey = jiraIssues.issues.get(i).key;
                    String workItemId = importedWorkItems.get(i).getId();
                    jiraIssueToPolarionWorkItemMapping.put(jiraIssueKey, workItemId);
                }
            } else {
                log.error("Error querying issues");
            }
        }

        saveMappingToFile(jiraIssueToPolarionWorkItemMapping);
    }

    private static void deleteMappingFile() throws IOException {
        String mappingFile = getMappingFile();
        Path file = Paths.get(mappingFile);
        if (Files.deleteIfExists(file)) {
            log.info("{} has been deleted", mappingFile);
        }
    }

    private static String getMappingFile() {
        return MigratorConfig.INSTANCE.getConfigurationPath() + File.separator + JIRA_TO_POLARION_MAPPING;
    }

    private static void saveMappingToFile(Map<String, String> jiraIssueToPolarionWorkItemMapping) throws IOException {
        Map<String, String> sortedMap = new TreeMap<>(jiraIssueToPolarionWorkItemMapping);

        String mappingFile = getMappingFile();
        Path file = Paths.get(mappingFile);
        Files.write(file, () -> sortedMap.entrySet().stream()
                .<CharSequence>map(entry -> entry.getKey() + " = " + entry.getValue())
                .iterator());

        log.info("jira issues -> polarion workitems mapping has been stored to {}", mappingFile);
    }

    @SneakyThrows
    private static List<JiraIssues> getJiraIssues() {
        String jiraIssuesFromFile = System.getProperty(JIRA_ISSUES_FROM_FILE);

        if (jiraIssuesFromFile != null) {
            return loadJiraIssuesFromFile(jiraIssuesFromFile);
        } else {
            return queryJiraIssues();
        }
    }

    private static List<JiraIssues> loadJiraIssuesFromFile(String filename) throws IOException {
        log.info("Jira Issues will be loaded from provided file = '{}'", filename);

        Path path = Paths.get(filename);
        try (Stream<String> lines = Files.lines(path)) {
            String content = lines.collect(Collectors.joining(System.lineSeparator()));
            JiraIssues jiraIssues = new ObjectMapper()
                    .readValue(content, JiraIssues.class);
            return List.of(jiraIssues);
        }
    }

    private static List<JiraIssues> queryJiraIssues() {
        String jql = new JqlBuilder().build();
        log.info("Trying to get jira issues using JQL query = '{}'", jql);

        JiraConnector jiraConnector = new JiraConnector();

        long count = jiraConnector.queryIssuesCount(jql);
        log.info("Total found {} jira issues", count);

        int maxResults = JiraConnector.MAX_RESULTS;
        int iterations = (int) (count / maxResults) + 1;
        List<JiraIssues> result = new ArrayList<>(iterations);

        for (int i = 0; i < iterations; i++) {
            int startAt = i * maxResults;
            log.info("Trying to get jira issues with startAt = {} and maxResults = {}", startAt, maxResults);
            JiraIssues jiraIssues = jiraConnector.queryIssues(jql, maxResults, startAt);
            result.add(jiraIssues);
        }

        return result;
    }
}
