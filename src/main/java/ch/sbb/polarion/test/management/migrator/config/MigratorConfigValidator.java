package ch.sbb.polarion.test.management.migrator.config;

import ch.sbb.polarion.test.management.migrator.exception.InvalidMigratorConfigurationException;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

@UtilityClass
public class MigratorConfigValidator {

    private static final Map<JiraSecurityType, List<String>> JIRA_AUTH_TYPE_PROPERTIES = Map.of(
            JiraSecurityType.BASIC, List.of(MigratorConfigConstants.JIRA_SECURITY_USERNAME, MigratorConfigConstants.JIRA_SECURITY_PASSWORD),
            JiraSecurityType.BEARER, List.of(MigratorConfigConstants.JIRA_SECURITY_PERSONAL_ACCESS_TOKEN),
            JiraSecurityType.OAUTH, List.of(MigratorConfigConstants.JIRA_SECURITY_OAUTH_TOKEN_REQUEST_URL, MigratorConfigConstants.JIRA_SECURITY_OAUTH_CLIENT_ID, MigratorConfigConstants.JIRA_SECURITY_OAUTH_CLIENT_SECRET)
    );

    private static final Map<JiraQueryType, List<String>> JIRA_QUERY_TYPE_PROPERTIES = Map.of(
            JiraQueryType.KEYS, List.of(MigratorConfigConstants.JIRA_QUERY_KEYS),
            JiraQueryType.JQL, List.of(MigratorConfigConstants.JIRA_QUERY_JQL)
    );

    public static void validateProperties(Properties properties) {
        validateObligatoryProperty(properties, MigratorConfigConstants.JIRA_BASE_URL);

        validateObligatoryProperty(properties, MigratorConfigConstants.JIRA_SECURITY_TYPE);
        validateObligatoryProperties(properties, JIRA_AUTH_TYPE_PROPERTIES.get(getJiraSecurityType(properties)));

        validateObligatoryProperty(properties, MigratorConfigConstants.JIRA_QUERY_TYPE);
        validateObligatoryProperties(properties, JIRA_QUERY_TYPE_PROPERTIES.get(getJiraQueryType(properties)));

        validateObligatoryProperty(properties, MigratorConfigConstants.POLARION_BASE_URL);
        validateObligatoryProperty(properties, MigratorConfigConstants.POLARION_SECURITY_ACCESS_TOKEN);
        validateObligatoryProperty(properties, MigratorConfigConstants.POLARION_TARGET_PROJECT);
    }

    private static JiraSecurityType getJiraSecurityType(Properties properties) {
        String value = properties.getProperty(MigratorConfigConstants.JIRA_SECURITY_TYPE);
        return JiraSecurityType.valueOf(value.toUpperCase(Locale.getDefault()));
    }

    private static JiraQueryType getJiraQueryType(Properties properties) {
        String value = properties.getProperty(MigratorConfigConstants.JIRA_QUERY_TYPE);
        return JiraQueryType.valueOf(value.toUpperCase(Locale.getDefault()));
    }

    private static void validateObligatoryProperty(Properties props, String property) {
        if (!props.containsKey(property) || props.getProperty(property).isEmpty()) {
            throw new InvalidMigratorConfigurationException(String.format("Configuration properties should contain obligatory property '%s'", property));
        }
    }

    private static void validateObligatoryProperties(Properties properties, List<String> propertyList) {
        propertyList
                .forEach(property -> validateObligatoryProperty(properties, property));
    }
}
