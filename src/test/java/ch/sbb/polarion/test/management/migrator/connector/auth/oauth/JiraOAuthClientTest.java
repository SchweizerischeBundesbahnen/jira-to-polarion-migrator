package ch.sbb.polarion.test.management.migrator.connector.auth.oauth;

import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.exception.OAuthRuntimeException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

class JiraOAuthClientTest {
    @Test
    void testGetTokenFailure() throws Exception {
        MigratorConfig migratorConfig = mock(MigratorConfig.class);
        when(migratorConfig.getJiraSecurityOAuthTokenRequestUrl()).thenReturn("https://example.com/token");
        when(migratorConfig.getJiraSecurityOAuthClientId()).thenReturn("client_id");
        when(migratorConfig.getJiraSecurityOAuthClientSecret()).thenReturn("client_secret");
        try (MockedStatic<OAuthClientRequest> staticRequest = mockStatic(OAuthClientRequest.class)) {
            OAuthClientRequest.TokenRequestBuilder builder = mock(OAuthClientRequest.TokenRequestBuilder.class);

            staticRequest.when(() -> OAuthClientRequest
                            .tokenLocation(anyString()))
                    .thenReturn(builder);

            when(builder.setGrantType(GrantType.CLIENT_CREDENTIALS)).thenReturn(builder);
            when(builder.setClientId(anyString())).thenReturn(builder);
            when(builder.setClientSecret(anyString())).thenReturn(builder);
            when(builder.buildBodyMessage()).thenThrow(new OAuthSystemException("mocked exception"));

            assertThrows(OAuthRuntimeException.class, () -> {
                JiraOAuthClient.getToken(migratorConfig);
            });
        }
    }
}
