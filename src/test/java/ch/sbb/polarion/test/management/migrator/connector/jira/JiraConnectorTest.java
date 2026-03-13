package ch.sbb.polarion.test.management.migrator.connector.jira;

import ch.sbb.polarion.test.management.migrator.BaseMockServerClass;
import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import ch.sbb.polarion.test.management.migrator.exception.JiraInvalidResponseException;
import ch.sbb.polarion.test.management.migrator.exception.JiraIssueNotFoundException;
import ch.sbb.polarion.test.management.migrator.model.jira.JiraIssues;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JiraConnectorTest extends BaseMockServerClass {

    private final JiraConnector connector;

    public JiraConnectorTest() throws IOException {
        Properties specificProperties = getSpecificProperties("mockserver/mockserver-config-jira.properties");
        MigratorConfig migratorConfig = new MigratorConfig();
        migratorConfig.setProperties(specificProperties);
        connector = new JiraConnector(migratorConfig);
    }

    @Test
    void getOneQueryIssue() throws IOException {
        String issuesKey = "(key=TESTPRJ-17)";
        mockCallToJira("mockserver/responses/connector/jira/TESTPRJ17.json", issuesKey);

        JiraIssues issues = connector.queryIssues(issuesKey);
        assertEquals(1, issues.issues.size());
        assertEquals("TESTPRJ-17", issues.issues.get(0).key);
    }

    @Test
    void getListOf3issue() throws IOException {
        String issuesKey = "(key=TESTPRJ-17 OR key=TESTPRJ-16 OR key=TESTPRJ-15)";
        mockCallToJira("mockserver/responses/connector/jira/TESTPRJ15-17.json", issuesKey);

        JiraIssues issues = connector.queryIssues(issuesKey);
        assertEquals(3, issues.issues.size());
        assertEquals("TESTPRJ-17", issues.issues.get(0).key);
        assertEquals("TESTPRJ-16", issues.issues.get(1).key);
        assertEquals("TESTPRJ-15", issues.issues.get(2).key);
    }

    @Test
    void getAll() throws IOException {

        String issuesKey = "";
        mockCallToJira("mockserver/responses/connector/jira/ALL.json", issuesKey);

        JiraIssues issues = connector.queryIssues(issuesKey);
        assertEquals(10, issues.issues.size());
    }

    @Test
    void getNotExistedIssue() throws IOException {
        String issuesKey = "(key=TESTPRJ-100)";
        mockCallToJiraWithError("mockserver/responses/connector/jira/TESTPRJ100.json", issuesKey);

        JiraInvalidResponseException thrown = Assertions.assertThrows(JiraInvalidResponseException.class, () -> connector.queryIssues(issuesKey));

        Assertions.assertEquals("Could not get response from Jira", thrown.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenIssueNotFound() {
        String issuesKey = "(key=TESTPRJ-100)";
        mockCallToJiraWithThrowExceptionWhenIssueNotFound(issuesKey);

        JiraIssueNotFoundException thrown = Assertions.assertThrows(JiraIssueNotFoundException.class, () -> connector.queryIssues(issuesKey));

        Assertions.assertEquals("No jira issues were found.", thrown.getMessage());
    }

    private void mockCallToJira(String pathToResponse, String jql) throws IOException {
        String content;
        try (InputStream inputStream = getResourceAsStream(pathToResponse)) {
            content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
        Assertions.assertNotNull(content);

        wireMockServer.stubFor(get(urlPathEqualTo("/rest/api/latest/search"))
                .withQueryParam("jql", equalTo(jql))
                .withQueryParam("expand", equalTo("renderedFields"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withHeader("Cache-Control", "public, max-age=86400")
                        .withBody(content)
                        .withFixedDelay(1000)));
    }

    private void mockCallToJiraWithError(String pathToResponse, String jql) throws IOException {
        String content;
        try (InputStream inputStream = getResourceAsStream(pathToResponse)) {
            content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
        Assertions.assertNotNull(content);

        wireMockServer.stubFor(get(urlPathEqualTo("/rest/api/latest/search"))
                .withQueryParam("jql", equalTo(jql))
                .withQueryParam("expand", equalTo("renderedFields"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withHeader("Cache-Control", "public, max-age=86400")
                        .withBody(content)
                        .withFixedDelay(1000)));
    }

    private void mockCallToJiraWithThrowExceptionWhenIssueNotFound(String jql) {
        wireMockServer.stubFor(get(urlPathEqualTo("/rest/api/latest/search"))
                .withQueryParam("jql", equalTo(jql))
                .withQueryParam("expand", equalTo("renderedFields"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withHeader("Cache-Control", "public, max-age=86400")
                        .withBody("""
                                {
                                  "expand": "schema,names",
                                  "startAt": 0,
                                  "maxResults": 0,
                                  "total": 0,
                                  "issues": []}""")
                        .withFixedDelay(1000)));
    }
}
