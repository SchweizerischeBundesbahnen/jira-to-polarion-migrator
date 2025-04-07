package ch.sbb.polarion.test.management.migrator;

import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import ch.sbb.polarion.test.management.migrator.model.jira.JiraIssues;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MigratorTest {

    @Test
    void getMappingFileShouldReturnCorrectPath() {
        // Act
        String mappingFile = Migrator.getMappingFile();

        // Assert
        assertTrue(mappingFile.endsWith("jira-to-polarion.mapping"));
    }

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
        List<JiraIssues> result = Migrator.loadJiraIssuesFromFile(tempFile.toString());

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
        List<JiraIssues> result = Migrator.getJiraIssues(new MigratorConfig());

        // Assert
        assertEquals(1, result.size());
        assertEquals("KEY-1", result.get(0).issues.get(0).key);
    }
}
