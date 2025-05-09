package ch.sbb.polarion.test.management.migrator.connector.auth.oauth;

import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import lombok.experimental.UtilityClass;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthRuntimeException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

@UtilityClass
public class JiraOAuthClient {

    public static String getToken(MigratorConfig migratorConfig) {
        try {
            OAuthClientRequest oAuthClientRequest = OAuthClientRequest
                    .tokenLocation(migratorConfig.getJiraSecurityOAuthTokenRequestUrl())
                    .setGrantType(GrantType.CLIENT_CREDENTIALS)
                    .setClientId(migratorConfig.getJiraSecurityOAuthClientId())
                    .setClientSecret(migratorConfig.getJiraSecurityOAuthClientSecret())
                    .buildBodyMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthJSONAccessTokenResponse oAuthJSONAccessTokenResponse = oAuthClient.accessToken(oAuthClientRequest);
            return oAuthJSONAccessTokenResponse.getAccessToken();
        } catch (OAuthSystemException | OAuthProblemException e) {
            throw new OAuthRuntimeException(e);
        }
    }
}
