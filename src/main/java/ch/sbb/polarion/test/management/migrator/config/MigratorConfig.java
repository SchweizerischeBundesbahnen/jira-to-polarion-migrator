package ch.sbb.polarion.test.management.migrator.config;

import ch.sbb.polarion.test.management.migrator.exception.InvalidMigratorConfigurationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MigratorConfig {
    protected static final String JIRA_TO_POLARION_MIGRATOR_PROPERTIES = "jira-to-polarion-migrator.properties";

    protected static final String JIRA_BASE_URL = "jira.base.url";

    protected static final String JIRA_SECURITY_TYPE = "jira.security.type";

    protected static final String JIRA_SECURITY_USERNAME = "jira.security.username";
    protected static final String JIRA_SECURITY_PASSWORD = "jira.security.password";
    protected static final String JIRA_SECURITY_PERSONAL_ACCESS_TOKEN = "jira.security.personal.access.token";

    protected static final String JIRA_SECURITY_OAUTH_TOKEN_REQUEST_URL = "jira.security.oauth.token.request.url";
    protected static final String JIRA_SECURITY_OAUTH_CLIENT_ID = "jira.security.oauth.client_id";
    protected static final String JIRA_SECURITY_OAUTH_CLIENT_SECRET = "jira.security.oauth.client_secret";

    protected static final String JIRA_QUERY_TYPE = "jira.query.type";

    protected static final String JIRA_QUERY_JQL = "jira.query.jql";
    protected static final String JIRA_QUERY_KEYS = "jira.query.keys";

    protected static final String POLARION_TEST_CASE_TYPE = "polarion.test.case.type";
    protected static final String POLARION_TEST_CASE_TESTTYPE = "polarion.test.case.testtype";
    protected static final String POLARION_TEST_CASE_STATUS = "polarion.test.case.status";
    protected static final String POLARION_TEST_CASE_SEVERITY = "polarion.test.case.severity";

    protected static final String POLARION_TEST_CASE_CUSTOM_FIELD_JIRA_ISSUE_ID = "polarion.test.case.custom.field.jira.issue.id";
    protected static final String POLARION_TEST_CASE_CUSTOM_FIELD_JIRA_ISSUE_URL = "polarion.test.case.custom.field.jira.issue.url";

    protected static final String POLARION_BASE_URL = "polarion.base.url";
    protected static final String POLARION_SECURITY_ACCESS_TOKEN = "polarion.security.access.token";

    protected static final String POLARION_TARGET_PROJECT = "polarion.target.project";

    @Setter
    private static Properties properties = new Properties();

    public static void loadConfig() {
        properties.clear();

        String config = getConfigurationPath() + File.separator + JIRA_TO_POLARION_MIGRATOR_PROPERTIES;

        try (InputStream input = new FileInputStream(config)) {
            log.info(JIRA_TO_POLARION_MIGRATOR_PROPERTIES + " found");
            properties.load(input);
        } catch (IOException e) {
            throw new InvalidMigratorConfigurationException("Error reading " + JIRA_TO_POLARION_MIGRATOR_PROPERTIES, e);
        }

        MigratorConfigValidator.validateProperties(properties);
        log.info(JIRA_TO_POLARION_MIGRATOR_PROPERTIES + " validated");
    }

    public static String getJiraBaseUrl() {
        return getStringProperty(JIRA_BASE_URL);
    }

    public static JiraSecurityType getJiraSecurityType() {
        String property = getStringProperty(JIRA_SECURITY_TYPE);
        if (property == null) {
            throw new InvalidMigratorConfigurationException(JIRA_SECURITY_TYPE + " is not configured properly");
        }
        return JiraSecurityType.valueOf(property.toUpperCase(Locale.getDefault()));
    }

    public static String getJiraSecurityUsername() {
        return getStringProperty(JIRA_SECURITY_USERNAME);
    }

    public static String getJiraSecurityPassword() {
        return getStringProperty(JIRA_SECURITY_PASSWORD);
    }

    public static String getJiraSecurityPersonalAccessToken() {
        return getStringProperty(JIRA_SECURITY_PERSONAL_ACCESS_TOKEN);
    }

    public static String getJiraSecurityOAuthTokenRequestUrl() {
        return getStringProperty(JIRA_SECURITY_OAUTH_TOKEN_REQUEST_URL);
    }

    public static String getJiraSecurityOAuthClientId() {
        return getStringProperty(JIRA_SECURITY_OAUTH_CLIENT_ID);
    }

    public static String getJiraSecurityOAuthClientSecret() {
        return getStringProperty(JIRA_SECURITY_OAUTH_CLIENT_SECRET);
    }

    public static JiraQueryType getJiraQueryType() {
        String property = getStringProperty(JIRA_QUERY_TYPE);
        if (property == null) {
            throw new InvalidMigratorConfigurationException(JIRA_QUERY_TYPE + " is not configured properly");
        }
        return JiraQueryType.valueOf(property.toUpperCase(Locale.getDefault()));
    }

    public static String getJiraQueryJql() {
        return getStringProperty(JIRA_QUERY_JQL);
    }

    public static String getJiraQueryKeys() {
        return getStringProperty(JIRA_QUERY_KEYS);
    }

    public static String getPolarionBaseUrl() {
        return getStringProperty(POLARION_BASE_URL);
    }

    public static String getPolarionSecurityAccessToken() {
        return getStringProperty(POLARION_SECURITY_ACCESS_TOKEN);
    }

    public static String getPolarionTargetProject() {
        return getStringProperty(POLARION_TARGET_PROJECT);
    }

    public static String getPolarionTestCaseType() {
        return getNonBlankStringProperty(POLARION_TEST_CASE_TYPE, "testcase");
    }

    public static String getPolarionTestCaseTesttype() {
        return getNonBlankStringProperty(POLARION_TEST_CASE_TESTTYPE, "TEST_TESTTYPE");
    }

    public static String getPolarionTestCaseStatus() {
        return getNonBlankStringProperty(POLARION_TEST_CASE_STATUS, "Draft");
    }

    public static String getPolarionTestCaseSeverity() {
        return getNonBlankStringProperty(POLARION_TEST_CASE_SEVERITY, "normal");
    }

    public static String getPolarionTestCaseCustomFieldJiraIssueId() {
        return getStringProperty(POLARION_TEST_CASE_CUSTOM_FIELD_JIRA_ISSUE_ID);
    }

    public static String getPolarionTestCaseCustomFieldJiraIssueUrl() {
        return getStringProperty(POLARION_TEST_CASE_CUSTOM_FIELD_JIRA_ISSUE_URL);
    }

    public static String getConfigurationPath() {
        return Paths.get("").toAbsolutePath().toString();
    }

    private static String getStringProperty(String property) {
        String value = properties.getProperty(property);
        return value == null ? null : value.trim();
    }

    private static String getNonBlankStringProperty(String property, String defaultValue) {
        String value = getStringProperty(property);
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
