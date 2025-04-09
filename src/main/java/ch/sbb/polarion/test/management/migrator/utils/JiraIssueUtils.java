package ch.sbb.polarion.test.management.migrator.utils;

import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import ch.sbb.polarion.test.management.migrator.connector.jira.JiraConnector;
import ch.sbb.polarion.test.management.migrator.connector.jira.jql.JqlBuilder;
import ch.sbb.polarion.test.management.migrator.model.jira.JiraIssues;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.VisibleForTesting;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@UtilityClass
public class JiraIssueUtils {

    public static final String JIRA_ISSUES_FROM_FILE = "jira.issues.from.file";

    @SneakyThrows
    public static List<JiraIssues> getJiraIssues(MigratorConfig config) {
        String jiraIssuesFromFile = System.getProperty(JIRA_ISSUES_FROM_FILE);

        if (jiraIssuesFromFile != null) {
            return loadJiraIssuesFromFile(jiraIssuesFromFile);
        } else {
            JiraConnector connector = new JiraConnector(config);
            return queryJiraIssues(config, connector);
        }
    }

    @VisibleForTesting
    static List<JiraIssues> loadJiraIssuesFromFile(String filename) throws IOException {
        log.info("Loading Jira Issues from file: '{}'", filename);

        Path path = Paths.get(filename);
        try (Stream<String> lines = Files.lines(path)) {
            String content = lines.collect(Collectors.joining(System.lineSeparator()));
            JiraIssues jiraIssues = new ObjectMapper().readValue(content, JiraIssues.class);
            return List.of(jiraIssues);
        }
    }

    @VisibleForTesting
    static List<JiraIssues> queryJiraIssues(MigratorConfig config, JiraConnector connector) {
        String jql = new JqlBuilder(config).build();
        log.info("Querying Jira with JQL: '{}'", jql);

        long count = connector.queryIssuesCount(jql);
        log.info("Found {} issues", count);

        int maxResults = JiraConnector.MAX_RESULTS;
        int iterations = (int) (count / maxResults) + 1;
        List<JiraIssues> result = new ArrayList<>();

        for (int i = 0; i < iterations; i++) {
            int startAt = i * maxResults;
            JiraIssues jiraIssues = connector.queryIssues(jql, maxResults, startAt);
            result.add(jiraIssues);
        }

        return result;
    }
}
