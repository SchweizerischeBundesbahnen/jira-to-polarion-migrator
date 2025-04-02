package ch.sbb.polarion.test.management.migrator.connector.auth.header;

import ch.sbb.polarion.test.management.migrator.config.JiraSecurityType;
import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import ch.sbb.polarion.test.management.migrator.connector.auth.oauth.JiraOAuthClient;
import ch.sbb.polarion.test.management.migrator.exception.InvalidMigratorConfigurationException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JiraHeaderBuilder implements HeaderBuilder {

    private static BasicAuthHeader createBasicAuthHeader() {
        return new BasicAuthHeader(MigratorConfig.INSTANCE.getJiraSecurityUsername(), MigratorConfig.INSTANCE.getJiraSecurityPassword());
    }

    private static BearerAuthHeader createBearerAuthHeader() {
        return new BearerAuthHeader(MigratorConfig.INSTANCE.getJiraSecurityPersonalAccessToken());
    }

    private static BearerAuthHeader createOAuthBearerAuthHeader() {
        String bearerToken = new JiraOAuthClient().getToken();
        return new BearerAuthHeader(bearerToken);
    }

    @Override
    public Header build() {
        JiraSecurityType jiraSecurityType = MigratorConfig.INSTANCE.getJiraSecurityType();
        return switch (jiraSecurityType) {
            case BASIC -> createBasicAuthHeader();
            case BEARER -> createBearerAuthHeader();
            case OAUTH -> createOAuthBearerAuthHeader();
            default -> throw new InvalidMigratorConfigurationException("Not supported auth type provided");
        };
    }
}
