package ch.sbb.polarion.test.management.migrator.connector.polarion;


import ch.sbb.polarion.test.management.migrator.BaseMockServerClass;
import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import ch.sbb.polarion.test.management.migrator.exception.ResponseParsingException;
import ch.sbb.polarion.test.management.migrator.exception.WorkItemsNotCreatedException;
import ch.sbb.polarion.test.management.migrator.model.polarion.WorkItems;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockserver.model.Header;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class PolarionConnectorTest extends BaseMockServerClass {

    private final PolarionConnector connector;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public PolarionConnectorTest() throws IOException {
        Properties specificProperties = getSpecificProperties("mockserver/mockserver-config-polarion.properties");
        MigratorConfig migratorConfig = new MigratorConfig();
        migratorConfig.setProperties(specificProperties);
        connector = new PolarionConnector(migratorConfig);
    }

    @Test
    void fromJiraIssues() throws IOException {
        String content;
        try (InputStream inputStream = getResourceAsStream("mockserver/requests/connector/polarion/oneWI.json")) {
            content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
        Assertions.assertNotNull(content);

        WorkItems workItems = objectMapper.readValue(content, WorkItems.class);

        mockCallToPolarion(workItems);

        WorkItems result = connector.importWorkItems(workItems);
        assertEquals(workItems.getData().size(), result.getData().size());
        assertNotNull(result.getData().get(0).getId());
    }


    @Test
    @SneakyThrows
    void shouldThrowWorkItemsNotCreatedExceptionWhenResponseIsNotCreated() {
        WorkItems workItems = new WorkItems();
        mockServer.when(
                        request()
                                .withMethod("POST")
                                .withPath("/polarion/rest/v1/projects/elibrary/workitems")
                                .withBody(objectMapper.writeValueAsString(workItems)),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(400)
                );

        WorkItemsNotCreatedException exception = assertThrows(
                WorkItemsNotCreatedException.class,
                () -> connector.importWorkItems(workItems)
        );

        assertEquals("WorkItems were not created!!!", exception.getMessage());
    }

    @Test
    @SneakyThrows
    void importWorkItemsThrowsResponseParsingExceptionWhenJsonIsInvalid() {
        WorkItems workItems = new WorkItems();
        mockServer.when(
                        request()
                                .withMethod("POST")
                                .withPath("/polarion/rest/v1/projects/elibrary/workitems")
                                .withBody(objectMapper.writeValueAsString(workItems)),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(201)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Cache-Control", "public, max-age=86400"))
                                .withBody("{ invalid_json")
                                .withDelay(TimeUnit.SECONDS, 1));

        ResponseParsingException exception = assertThrows(
                ResponseParsingException.class,
                () -> connector.importWorkItems(workItems)
        );

        assertInstanceOf(ResponseParsingException.class, exception);
        assertInstanceOf(JsonParseException.class, exception.getCause());
    }

    private void mockCallToPolarion(WorkItems workItems) throws IOException {
        String content;
        try (InputStream inputStream = getResourceAsStream("mockserver/responses/connector/polarion/createdWI.json")) {
            content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
        Assertions.assertNotNull(content);

        mockServer.when(
                        request()
                                .withMethod("POST")
                                .withPath("/polarion/rest/v1/projects/elibrary/workitems")
                                .withBody(objectMapper.writeValueAsString(workItems)),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(201)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Cache-Control", "public, max-age=86400"))
                                .withBody(content)
                                .withDelay(TimeUnit.SECONDS, 1));
    }
}
