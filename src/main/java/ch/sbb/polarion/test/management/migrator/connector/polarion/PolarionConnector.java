package ch.sbb.polarion.test.management.migrator.connector.polarion;

import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import ch.sbb.polarion.test.management.migrator.connector.auth.header.Header;
import ch.sbb.polarion.test.management.migrator.connector.auth.header.PolarionHeaderBuilder;
import ch.sbb.polarion.test.management.migrator.exception.ResponseParsingException;
import ch.sbb.polarion.test.management.migrator.exception.WorkItemsNotCreatedException;
import ch.sbb.polarion.test.management.migrator.model.polarion.WorkItems;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

@Slf4j
@AllArgsConstructor
public class PolarionConnector {

    private Header header;

    public PolarionConnector() {
        header = new PolarionHeaderBuilder().build();
    }

    public WorkItems importWorkItems(WorkItems workItems) {
        String workItemsEndpoint = String.format("%s/polarion/rest/v1/projects/%s/workitems", MigratorConfig.INSTANCE.getPolarionBaseUrl(), MigratorConfig.INSTANCE.getPolarionTargetProject());

        try (Client client = ClientBuilder.newClient(new ClientConfig()).register(JacksonFeature.class)) {
            WebTarget webTarget = client.target(workItemsEndpoint);

            try (Response response = webTarget.request(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, header.toString())
                    .post(Entity.entity(workItems, MediaType.APPLICATION_JSON))) {

                log.info("Sent request: {}", webTarget.getUri());
                log.info("Response status: {}", response.getStatus() + " " + Response.Status.fromStatusCode(response.getStatus()));

                String responseContent = response.readEntity(String.class);
                if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
                    log.debug("Response content: {}", responseContent);

                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        return objectMapper.readValue(responseContent, WorkItems.class);
                    } catch (JsonProcessingException e) {
                        throw new ResponseParsingException(e);
                    }
                } else {
                    log.error("Response content: {}", responseContent);
                    throw new WorkItemsNotCreatedException("WorkItems were not created!!!");
                }
            }
        }
    }
}
