package ch.sbb.polarion.test.management.migrator.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

import static ch.sbb.polarion.test.management.migrator.config.MigratorConfig.*;
import static java.util.Map.entry;

class MigratorConfigTest {

    private static Stream<Arguments> testValuesForValidateConfigFile() {
        return Stream.of(
                Arguments.of(
                        Map.ofEntries(
                                entry(POLARION_TEST_CASE_TYPE, "custom_testcase"),
                                entry(POLARION_TEST_CASE_TESTTYPE, "custom_testtype"),
                                entry(POLARION_TEST_CASE_STATUS, "custom_status"),
                                entry(POLARION_TEST_CASE_SEVERITY, "custom_secerity"),
                                entry(JIRA_SECURITY_PERSONAL_ACCESS_TOKEN, "jira_token"),
                                entry(JIRA_SECURITY_OAUTH_TOKEN_REQUEST_URL, "jira_url"),
                                entry(JIRA_SECURITY_OAUTH_CLIENT_ID, "client_id"),
                                entry(JIRA_SECURITY_OAUTH_CLIENT_SECRET, "secret")
                        ),
                        Map.ofEntries(
                                entry(POLARION_TEST_CASE_TYPE, "custom_testcase"),
                                entry(POLARION_TEST_CASE_TESTTYPE, "custom_testtype"),
                                entry(POLARION_TEST_CASE_STATUS, "custom_status"),
                                entry(POLARION_TEST_CASE_SEVERITY, "custom_secerity"),
                                entry(JIRA_SECURITY_PERSONAL_ACCESS_TOKEN, "jira_token"),
                                entry(JIRA_SECURITY_OAUTH_TOKEN_REQUEST_URL, "jira_url"),
                                entry(JIRA_SECURITY_OAUTH_CLIENT_ID, "client_id"),
                                entry(JIRA_SECURITY_OAUTH_CLIENT_SECRET, "secret")
                        )
                ),
                Arguments.of(
                        Map.ofEntries(
                        ),
                        Map.ofEntries(
                                entry(POLARION_TEST_CASE_TYPE, "testcase"),
                                entry(POLARION_TEST_CASE_TESTTYPE, "TEST_TESTTYPE"),
                                entry(POLARION_TEST_CASE_STATUS, "Draft"),
                                entry(POLARION_TEST_CASE_SEVERITY, "normal")
                        )
                ),
                Arguments.of(
                        Map.ofEntries(
                                entry(POLARION_TEST_CASE_TYPE, ""),
                                entry(POLARION_TEST_CASE_TESTTYPE, ""),
                                entry(POLARION_TEST_CASE_STATUS, ""),
                                entry(POLARION_TEST_CASE_SEVERITY, "")
                        ),
                        Map.ofEntries(
                                entry(POLARION_TEST_CASE_TYPE, "testcase"),
                                entry(POLARION_TEST_CASE_TESTTYPE, "TEST_TESTTYPE"),
                                entry(POLARION_TEST_CASE_STATUS, "Draft"),
                                entry(POLARION_TEST_CASE_SEVERITY, "normal")
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("testValuesForValidateConfigFile")
    void testPolarionTestCaseConfig(Map<String, String> values, Map<String, String> expected) {
        Properties specificProperties = new Properties();
        for (Map.Entry<String, String> stringStringEntry : values.entrySet()) {
            specificProperties.setProperty(stringStringEntry.getKey(), stringStringEntry.getValue());
        }
        MigratorConfig migratorConfig = new MigratorConfig();
        migratorConfig.setProperties(specificProperties);

        Assertions.assertEquals(expected.get(POLARION_TEST_CASE_TYPE), migratorConfig.getPolarionTestCaseType());
        Assertions.assertEquals(expected.get(POLARION_TEST_CASE_TESTTYPE), migratorConfig.getPolarionTestCaseTesttype());
        Assertions.assertEquals(expected.get(POLARION_TEST_CASE_STATUS), migratorConfig.getPolarionTestCaseStatus());
        Assertions.assertEquals(expected.get(POLARION_TEST_CASE_SEVERITY), migratorConfig.getPolarionTestCaseSeverity());

        Assertions.assertEquals(expected.get(JIRA_SECURITY_PERSONAL_ACCESS_TOKEN), migratorConfig.getJiraSecurityPersonalAccessToken());
        Assertions.assertEquals(expected.get(JIRA_SECURITY_OAUTH_TOKEN_REQUEST_URL), migratorConfig.getJiraSecurityOAuthTokenRequestUrl());
        Assertions.assertEquals(expected.get(JIRA_SECURITY_OAUTH_CLIENT_ID), migratorConfig.getJiraSecurityOAuthClientId());
        Assertions.assertEquals(expected.get(JIRA_SECURITY_OAUTH_CLIENT_SECRET), migratorConfig.getJiraSecurityOAuthClientSecret());

    }
}
