package ch.sbb.polarion.test.management.migrator.config;


import ch.sbb.polarion.test.management.migrator.exception.InvalidMigratorConfigurationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.stream.Stream;

class MigratorConfigValidatorTest {

    private static Stream<Arguments> testValuesForValidateConfigFile() {
        return Stream.of(
                Arguments.of("config/00-no-jira-base-url.properties", missingConfigProperty("jira.base.url")),

                Arguments.of("config/01-no-jira-security-type.properties", missingConfigProperty("jira.security.type")),
                Arguments.of("config/02-jira-basic-no-username.properties", missingConfigProperty("jira.security.username")),
                Arguments.of("config/03-jira-basic-no-password.properties", missingConfigProperty("jira.security.password")),
                Arguments.of("config/04-jira-bearer-no-token.properties", missingConfigProperty("jira.security.personal.access.token")),
                Arguments.of("config/05-jira-oauth-no-url.properties", missingConfigProperty("jira.security.oauth.token.request.url")),
                Arguments.of("config/06-jira-oauth-no-client-id.properties", missingConfigProperty("jira.security.oauth.client_id")),
                Arguments.of("config/07-jira-oauth-no-client-secret.properties", missingConfigProperty("jira.security.oauth.client_secret")),

                Arguments.of("config/08-no-jira-query-type.properties", missingConfigProperty("jira.query.type")),
                Arguments.of("config/09-jira-query-jql-no-jql.properties", missingConfigProperty("jira.query.jql")),
                Arguments.of("config/10-jira-query-keys-no-keys.properties", missingConfigProperty("jira.query.keys")),

                Arguments.of("config/11-no-polarion-base-url.properties", missingConfigProperty("polarion.base.url")),

                Arguments.of("config/12-no-polarion-security-access-token.properties", missingConfigProperty("polarion.security.access.token")),

                Arguments.of("config/13-no-polarion-target-project.properties", missingConfigProperty("polarion.target.project"))
        );
    }

    private static String missingConfigProperty(String what) {
        return "Configuration properties should contain obligatory property '" + what + "'";
    }

    @ParameterizedTest
    @MethodSource("testValuesForValidateConfigFile")
    void validateConfigFile(String filename, String expectedErrorMessage) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename)) {
            Assertions.assertNotNull(inputStream, "unable to find " + filename);

            Properties props = new Properties();
            props.load(inputStream);

            InvalidMigratorConfigurationException thrown = Assertions.assertThrows(InvalidMigratorConfigurationException.class, () ->
                    MigratorConfigValidator.validateProperties(props)
            );

            Assertions.assertEquals(expectedErrorMessage, thrown.getMessage());
        }
    }

}
