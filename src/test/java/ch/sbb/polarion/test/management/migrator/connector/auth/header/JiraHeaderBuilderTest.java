package ch.sbb.polarion.test.management.migrator.connector.auth.header;

import ch.sbb.polarion.test.management.migrator.config.JiraSecurityType;
import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import ch.sbb.polarion.test.management.migrator.connector.auth.oauth.JiraOAuthClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JiraHeaderBuilderTest {
    private JiraHeaderBuilder headerBuilder;

    @BeforeEach
    void setup() {
        headerBuilder = new JiraHeaderBuilder();
    }

    @Test
    void testBasicAuthHeader() {
        MigratorConfig config = mock(MigratorConfig.class);
        when(config.getJiraSecurityType()).thenReturn(JiraSecurityType.BASIC);
        when(config.getJiraSecurityUsername()).thenReturn("user");
        when(config.getJiraSecurityPassword()).thenReturn("pass");

        Header header = headerBuilder.build(config);
        assertInstanceOf(BasicAuthHeader.class, header);
    }

    @Test
    void testBearerAuthHeader() {
        MigratorConfig config = mock(MigratorConfig.class);
        when(config.getJiraSecurityType()).thenReturn(JiraSecurityType.BEARER);
        when(config.getJiraSecurityPersonalAccessToken()).thenReturn("token123");

        Header header = headerBuilder.build(config);
        assertInstanceOf(BearerAuthHeader.class, header);
        BearerAuthHeader bearerHeader = (BearerAuthHeader) header;
        assertEquals("token123", bearerHeader.getToken());
    }

    @Test
    void testOAuthAuthHeader() {
        MigratorConfig config = mock(MigratorConfig.class);
        when(config.getJiraSecurityType()).thenReturn(JiraSecurityType.OAUTH);

        try (MockedStatic<JiraOAuthClient> mocked = Mockito.mockStatic(JiraOAuthClient.class)) {
            when(JiraOAuthClient.getToken(config)).thenReturn("oauth-token");

            Header header = headerBuilder.build(config);
            assertInstanceOf(BearerAuthHeader.class, header);
            BearerAuthHeader bearerHeader = (BearerAuthHeader) header;
            assertEquals("oauth-token", bearerHeader.getToken());
        }
    }
}
