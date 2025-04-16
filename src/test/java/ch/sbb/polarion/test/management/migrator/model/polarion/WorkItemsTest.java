package ch.sbb.polarion.test.management.migrator.model.polarion;

import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import ch.sbb.polarion.test.management.migrator.model.jira.Issue;
import ch.sbb.polarion.test.management.migrator.model.jira.JiraIssues;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.stream.Stream;

import static ch.sbb.polarion.test.management.migrator.model.polarion.WorkItems.PRIORITY_MAP;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WorkItemsTest {

    private static final String PROJECT_ID = "XRAYPRJ";
    private static final String JIRA_BASE_URL = "https://jira.example.com";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static Stream<Arguments> testValuesForFromJiraIssuesTest() {
        return Stream.of(
                Arguments.of("model/polarion/oneIssueWithoutPriority.json", 1, "", "", ""),
                Arguments.of("model/polarion/emptyIssuesList.json", 0, "", "", ""),
                Arguments.of("model/polarion/TESTPRJ-50.json", 50, "", "", ""),
                Arguments.of("model/polarion/TESTPRJ-300.json", 300, "", "", ""),
                Arguments.of("model/polarion/TESTPRJ-300.json", 300, "jiraIssueID", "jiraIssueURL", "customfield_11200.value")
        );
    }

    private static String getExpectedPriorityFromIssue(Issue issue) {
        return issue.fields.priority == null ? null : PRIORITY_MAP.get(issue.fields.priority.name);
    }

    @ParameterizedTest
    @MethodSource("testValuesForFromJiraIssuesTest")
    void testFromJiraIssues(String filename, int count, String polarionCustomFieldJiraIssueId, String polarionCustomFieldJiraIssueUrl, String customFields) throws IOException {
        Properties specificProperties = new Properties();
        specificProperties.setProperty("jira.base.url", JIRA_BASE_URL);
        specificProperties.setProperty("polarion.target.project", PROJECT_ID);
        specificProperties.setProperty("polarion.test.case.custom.field.jira.issue.id", polarionCustomFieldJiraIssueId);
        specificProperties.setProperty("polarion.test.case.custom.field.jira.issue.url", polarionCustomFieldJiraIssueUrl);
        specificProperties.setProperty("polarion.test.case.custom.fields", customFields);
        MigratorConfig migratorConfig = new MigratorConfig();
        migratorConfig.setProperties(specificProperties);

        String content;
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename)) {
            Assertions.assertNotNull(inputStream);
            content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
        Assertions.assertNotNull(content);

        JiraIssues issues = OBJECT_MAPPER.readValue(content, JiraIssues.class);

        WorkItems workItems = new WorkItems();
        workItems.fromJiraIssues(issues.issues, migratorConfig);

        assertEquals(count, issues.issues.size());
        assertEquals(issues.issues.size(), workItems.getData().size());

        for (int i = 0; i < issues.issues.size(); i++) {
            Issue issue = issues.issues.get(i);
            WorkItem wi = workItems.getData().get(i);

            assertEquals(issue.getAdditionalProperties().size(), wi.getAdditionalProperties().size());

            assertEquals(issue.fields.summary, wi.getAttributes().getTitle());
            assertEquals("testcase", wi.getAttributes().getType());
            assertEquals("TEST_TESTTYPE", wi.getAttributes().getTestType());
            assertEquals("normal", wi.getAttributes().getSeverity());
            assertEquals("Draft", wi.getAttributes().getStatus());
            assertEquals("text/html", wi.getAttributes().getDescription().getType());
            assertEquals(issue.renderedFields.getDescription(), wi.getAttributes().getDescription().getValue());
            assertEquals(getExpectedPriorityFromIssue(issue), wi.getAttributes().getPriority());

            if (!polarionCustomFieldJiraIssueId.isEmpty()) {
                assertEquals(issue.key, wi.getAttributes().getAdditionalProperties().get(polarionCustomFieldJiraIssueId));
            }
            if (!polarionCustomFieldJiraIssueUrl.isEmpty()) {
                assertEquals(JIRA_BASE_URL + "/browse/" + issue.key, wi.getAttributes().getAdditionalProperties().get(polarionCustomFieldJiraIssueUrl));
            }
            if (!customFields.isEmpty()) {
                assertEquals("Integrationstest", wi.getAttributes().getAdditionalProperties().get(customFields));
            }
        }
    }
}
