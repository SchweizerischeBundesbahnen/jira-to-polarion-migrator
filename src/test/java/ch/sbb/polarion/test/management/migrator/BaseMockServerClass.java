package ch.sbb.polarion.test.management.migrator;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseMockServerClass {
    protected WireMockServer wireMockServer;

    @BeforeAll
    public void startServer() {
        wireMockServer = new WireMockServer(wireMockConfig().port(1080));
        wireMockServer.start();
    }

    @AfterAll
    public void stopServer() {
        wireMockServer.stop();
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
