package ch.sbb.polarion.test.management.migrator.connector.jira;

import ch.sbb.polarion.test.management.migrator.BaseMockServerClass;
import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import ch.sbb.polarion.test.management.migrator.model.jira.JiraIssues;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockserver.model.Header;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

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

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> connector.queryIssues(issuesKey));

        Assertions.assertEquals("Could not get response from Jira", thrown.getMessage());
    }

    private void mockCallToJira(String pathToResponse, String jql) throws IOException {
        String content;
        try (InputStream inputStream = getResourceAsStream(pathToResponse)) {
            content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
        Assertions.assertNotNull(content);

        mockServer.when(
                        request()
                                .withMethod("GET")
                                .withPath("/rest/api/latest/search")
                                .withQueryStringParameter("jql", jql)
                                .withQueryStringParameter("expand", "renderedFields"),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Cache-Control", "public, max-age=86400"))
                                .withBody(content)
                                .withDelay(TimeUnit.SECONDS, 1));
    }

    private void mockCallToJiraWithError(String pathToResponse, String jql) throws IOException {
        String content;
        try (InputStream inputStream = getResourceAsStream(pathToResponse)) {
            content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
        Assertions.assertNotNull(content);

        mockServer.when(
                        request()
                                .withMethod("GET")
                                .withPath("/rest/api/latest/search")
                                .withQueryStringParameter("jql", jql)
                                .withQueryStringParameter("expand", "renderedFields"),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(400)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Cache-Control", "public, max-age=86400"))
                                .withBody(content)
                                .withDelay(TimeUnit.SECONDS, 1));
    }
}
