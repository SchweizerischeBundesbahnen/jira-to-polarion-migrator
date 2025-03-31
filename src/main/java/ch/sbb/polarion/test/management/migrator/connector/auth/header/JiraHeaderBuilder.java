package ch.sbb.polarion.test.management.migrator.connector.auth.header;

import ch.sbb.polarion.test.management.migrator.config.JiraSecurityType;
import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import ch.sbb.polarion.test.management.migrator.connector.auth.oauth.JiraOAuthClient;
import ch.sbb.polarion.test.management.migrator.exception.InvalidMigratorConfigurationException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JiraHeaderBuilder implements HeaderBuilder {

    private static BasicAuthHeader createBasicAuthHeader() {
        return new BasicAuthHeader(MigratorConfig.getInstance().getJiraSecurityUsername(), MigratorConfig.getInstance().getJiraSecurityPassword());
    }

    private static BearerAuthHeader createBearerAuthHeader() {
        return new BearerAuthHeader(MigratorConfig.getInstance().getJiraSecurityPersonalAccessToken());
    }

    private static BearerAuthHeader createOAuthBearerAuthHeader() {
        String bearerToken = new JiraOAuthClient().getToken();
        return new BearerAuthHeader(bearerToken);
    }

    @Override
    public Header build() {
        JiraSecurityType jiraSecurityType = MigratorConfig.getInstance().getJiraSecurityType();
        switch (jiraSecurityType) {
            case BASIC:
                return createBasicAuthHeader();
            case BEARER:
                return createBearerAuthHeader();
            case OAUTH:
                return createOAuthBearerAuthHeader();
            default:
                throw new InvalidMigratorConfigurationException("Not supported auth type provided");
        }
    }
}
