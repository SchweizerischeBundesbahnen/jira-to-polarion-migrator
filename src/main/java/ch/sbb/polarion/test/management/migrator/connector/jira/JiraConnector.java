package ch.sbb.polarion.test.management.migrator.connector.jira;

import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import ch.sbb.polarion.test.management.migrator.connector.auth.header.Header;
import ch.sbb.polarion.test.management.migrator.connector.auth.header.JiraHeaderBuilder;
import ch.sbb.polarion.test.management.migrator.exception.JiraInvalidResponseException;
import ch.sbb.polarion.test.management.migrator.exception.JiraIssueNotFoundException;
import ch.sbb.polarion.test.management.migrator.exception.ResponseParsingException;
import ch.sbb.polarion.test.management.migrator.model.jira.JiraIssues;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.client.ClientConfig;

import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class JiraConnector {

    public static final int MAX_RESULTS = 25;

    private final Header header;

    public JiraConnector() {
        header = new JiraHeaderBuilder().build();
    }

    public long queryIssuesCount(String query) {
        JiraIssues jiraIssues = queryIssues(query, 0, 0);
        return jiraIssues != null ? jiraIssues.total : 0;
    }

    public JiraIssues queryIssues(String query) {
        return queryIssues(query, MAX_RESULTS, 0);
    }

    public JiraIssues queryIssues(String query, long maxResults, long startAt) {
        String searchEndpoint = String.format("%s/rest/api/latest/search", MigratorConfig.getInstance().getJiraBaseUrl());

        try (Client client = ClientBuilder.newClient(new ClientConfig())) {
            WebTarget webTarget = client.target(searchEndpoint)
                    .queryParam("jql", query)
                    .queryParam("expand", "renderedFields")
                    .queryParam("maxResults", maxResults)
                    .queryParam("startAt", startAt);

            try (Response response = webTarget.request(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, header.toString())
                    .get()) {

                log.info("Sent request: {}", webTarget.getUri());

                String responseContent = response.readEntity(String.class);

                if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                    log.info("Response status: {}", response.getStatus() + " " + Response.Status.fromStatusCode(response.getStatus()));

                    log.debug("Response content: {}", responseContent);

                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        JiraIssues result = objectMapper.readValue(responseContent, JiraIssues.class);

                        log.info("Received {} jira issues", result.issues.size());
                        String issueKeys = result.issues.stream()
                                .map(issue -> issue.key)
                                .collect(Collectors.joining(";"));
                        log.info("Issue keys = '{}'", issueKeys);

                        if (result.total == 0) {
                            String errorMessage = "No jira issues were found.";
                            log.error(errorMessage);
                            throw new JiraIssueNotFoundException(errorMessage);
                        }

                        return result;
                    } catch (JsonProcessingException e) {
                        throw new ResponseParsingException(e);
                    }
                } else {
                    log.error("Response status: {}", response.getStatus() + " " + Response.Status.fromStatusCode(response.getStatus()));
                    log.error("Response: {}", responseContent);
                    throw new JiraInvalidResponseException("Could not get response from Jira");
                }
            }
        }
    }
}
