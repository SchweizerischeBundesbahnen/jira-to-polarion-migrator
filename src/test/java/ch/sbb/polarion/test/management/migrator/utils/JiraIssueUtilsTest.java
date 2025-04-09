package ch.sbb.polarion.test.management.migrator.utils;

import ch.sbb.polarion.test.management.migrator.config.JiraQueryType;
import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import ch.sbb.polarion.test.management.migrator.connector.jira.JiraConnector;
import ch.sbb.polarion.test.management.migrator.model.jira.JiraIssues;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JiraIssueUtilsTest {

    @Test
    @SneakyThrows
    void loadJiraIssuesFromFileShouldParseJsonFile() {
        // Arrange
        String json = """
                {
                  "issues": [
                    { "key": "JIRA-1" },
                    { "key": "JIRA-2" }
                  ]
                }
                """;

        Path tempFile = Files.createTempFile("jira-issues", ".json");
        Files.write(tempFile, json.getBytes());

        // Act
        List<JiraIssues> result = JiraIssueUtils.loadJiraIssuesFromFile(tempFile.toString());

        // Assert
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).issues.size());
        assertEquals("JIRA-1", result.get(0).issues.get(0).key);
    }


    @Test
    void getJiraIssuesShouldLoadFromFileWhenSystemPropertySet() throws Exception {
        // Arrange
        String json = """
                { "issues": [ { "key": "KEY-1" } ] }
                """;
        Path tempFile = Files.createTempFile("jira", ".json");
        Files.write(tempFile, json.getBytes());
        System.setProperty("jira.issues.from.file", tempFile.toString());

        // Act
        List<JiraIssues> result = JiraIssueUtils.getJiraIssues(new MigratorConfig());

        // Assert
        assertEquals(1, result.size());
        assertEquals("KEY-1", result.get(0).issues.get(0).key);
    }

    @Test
    void testQueryJiraIssues() {
        // Arrange
        MigratorConfig config = new MigratorConfig() {
            @Override
            public JiraQueryType getJiraQueryType() {
                return JiraQueryType.JQL;
            }
        };
        JiraConnector connector = mock(JiraConnector.class);
        String jql = "project = TEST";


        when(connector.queryIssuesCount(jql)).thenReturn(1L);
        when(connector.queryIssues(eq(jql), anyInt(), anyInt()))
                .thenReturn(new JiraIssues());

        // Act
        List<JiraIssues> result = JiraIssueUtils.queryJiraIssues(config, connector);

        // Assert
        assertEquals(1, result.size());
    }
}
