package ch.sbb.polarion.test.management.migrator;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockserver.integration.ClientAndServer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseMockServerClass {
    protected ClientAndServer mockServer;

    @BeforeAll
    public void startServer() {
        mockServer = startClientAndServer(1080);
    }

    @AfterAll
    public void stopServer() {
        mockServer.stop();
    }

    protected Properties getSpecificProperties(String mockServerConfigFile) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(mockServerConfigFile)) {
            Assertions.assertNotNull(inputStream, "Unable to find " + mockServerConfigFile);

            Properties props = new Properties();
            props.load(inputStream);
            return props;
        }
    }

    protected InputStream getResourceAsStream(String name) {
        return getClass().getClassLoader().getResourceAsStream(name);
    }
}
