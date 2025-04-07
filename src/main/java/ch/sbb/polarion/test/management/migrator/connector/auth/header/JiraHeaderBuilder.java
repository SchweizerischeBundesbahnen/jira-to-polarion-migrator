package ch.sbb.polarion.test.management.migrator.connector.auth.header;

import ch.sbb.polarion.test.management.migrator.config.JiraSecurityType;
import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import ch.sbb.polarion.test.management.migrator.connector.auth.oauth.JiraOAuthClient;
import ch.sbb.polarion.test.management.migrator.exception.InvalidMigratorConfigurationException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JiraHeaderBuilder implements HeaderBuilder {

    private static BasicAuthHeader createBasicAuthHeader(MigratorConfig migratorConfig) {
        return new BasicAuthHeader(migratorConfig.getJiraSecurityUsername(), migratorConfig.getJiraSecurityPassword());
    }

    private static BearerAuthHeader createBearerAuthHeader(MigratorConfig migratorConfig) {
        return new BearerAuthHeader(migratorConfig.getJiraSecurityPersonalAccessToken());
    }

    private static BearerAuthHeader createOAuthBearerAuthHeader(MigratorConfig migratorConfig) {
        String bearerToken = new JiraOAuthClient().getToken(migratorConfig);
        return new BearerAuthHeader(bearerToken);
    }

    @Override
    public Header build(MigratorConfig migratorConfig) {
        JiraSecurityType jiraSecurityType = migratorConfig.getJiraSecurityType();
        return switch (jiraSecurityType) {
            case BASIC -> createBasicAuthHeader(migratorConfig);
            case BEARER -> createBearerAuthHeader(migratorConfig);
            case OAUTH -> createOAuthBearerAuthHeader(migratorConfig);
            default -> throw new InvalidMigratorConfigurationException("Not supported auth type provided");
        };
    }
}
