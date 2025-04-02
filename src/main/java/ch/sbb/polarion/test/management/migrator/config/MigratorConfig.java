package ch.sbb.polarion.test.management.migrator.config;

import ch.sbb.polarion.test.management.migrator.exception.InvalidMigratorConfigurationException;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;


public enum MigratorConfig implements MigratorConfigConstants {

    INSTANCE;

    private static final Logger log = LoggerFactory.getLogger(MigratorConfig.class);

    @Setter
    private Properties properties = new Properties();

    public void loadConfig() {
        properties.clear();

        String configPath = getConfigurationPath() + File.separator + JIRA_TO_POLARION_MIGRATOR_PROPERTIES;

        try (InputStream input = new FileInputStream(configPath)) {
            log.info("{} found", JIRA_TO_POLARION_MIGRATOR_PROPERTIES);
            properties.load(input);
        } catch (IOException e) {
            throw new InvalidMigratorConfigurationException("Error reading " + JIRA_TO_POLARION_MIGRATOR_PROPERTIES, e);
        }

        MigratorConfigValidator.validateProperties(properties);
        log.info("{} validated", JIRA_TO_POLARION_MIGRATOR_PROPERTIES);
    }

    public String getJiraBaseUrl() {
        return getStringProperty(JIRA_BASE_URL);
    }

    public JiraSecurityType getJiraSecurityType() {
        String property = getStringProperty(JIRA_SECURITY_TYPE);
        if (property == null) {
            throw new InvalidMigratorConfigurationException(JIRA_SECURITY_TYPE + " is not configured properly");
        }
        return JiraSecurityType.valueOf(property.toUpperCase(Locale.getDefault()));
    }

    public String getJiraSecurityUsername() {
        return getStringProperty(JIRA_SECURITY_USERNAME);
    }

    public String getJiraSecurityPassword() {
        return getStringProperty(JIRA_SECURITY_PASSWORD);
    }

    public String getJiraSecurityPersonalAccessToken() {
        return getStringProperty(JIRA_SECURITY_PERSONAL_ACCESS_TOKEN);
    }

    public String getJiraSecurityOAuthTokenRequestUrl() {
        return getStringProperty(JIRA_SECURITY_OAUTH_TOKEN_REQUEST_URL);
    }

    public String getJiraSecurityOAuthClientId() {
        return getStringProperty(JIRA_SECURITY_OAUTH_CLIENT_ID);
    }

    public String getJiraSecurityOAuthClientSecret() {
        return getStringProperty(JIRA_SECURITY_OAUTH_CLIENT_SECRET);
    }

    public JiraQueryType getJiraQueryType() {
        String property = getStringProperty(JIRA_QUERY_TYPE);
        if (property == null) {
            throw new InvalidMigratorConfigurationException(JIRA_QUERY_TYPE + " is not configured properly");
        }
        return JiraQueryType.valueOf(property.toUpperCase(Locale.getDefault()));
    }

    public String getJiraQueryJql() {
        return getStringProperty(JIRA_QUERY_JQL);
    }

    public String getJiraQueryKeys() {
        return getStringProperty(JIRA_QUERY_KEYS);
    }

    public String getPolarionBaseUrl() {
        return getStringProperty(POLARION_BASE_URL);
    }

    public String getPolarionSecurityAccessToken() {
        return getStringProperty(POLARION_SECURITY_ACCESS_TOKEN);
    }

    public String getPolarionTargetProject() {
        return getStringProperty(POLARION_TARGET_PROJECT);
    }

    public String getPolarionTestCaseType() {
        return getNonBlankStringProperty(POLARION_TEST_CASE_TYPE, "testcase");
    }

    public String getPolarionTestCaseTesttype() {
        return getNonBlankStringProperty(POLARION_TEST_CASE_TESTTYPE, "TEST_TESTTYPE");
    }

    public String getPolarionTestCaseStatus() {
        return getNonBlankStringProperty(POLARION_TEST_CASE_STATUS, "Draft");
    }

    public String getPolarionTestCaseSeverity() {
        return getNonBlankStringProperty(POLARION_TEST_CASE_SEVERITY, "normal");
    }

    public String getPolarionTestCaseCustomFieldJiraIssueId() {
        return getStringProperty(POLARION_TEST_CASE_CUSTOM_FIELD_JIRA_ISSUE_ID);
    }

    public String getPolarionTestCaseCustomFieldJiraIssueUrl() {
        return getStringProperty(POLARION_TEST_CASE_CUSTOM_FIELD_JIRA_ISSUE_URL);
    }

    public String getConfigurationPath() {
        return Paths.get("").toAbsolutePath().toString();
    }

    private String getStringProperty(String property) {
        String value = properties.getProperty(property);
        return value == null ? null : value.trim();
    }

    private String getNonBlankStringProperty(String property, String defaultValue) {
        String value = getStringProperty(property);
        return (value == null || value.isBlank()) ? defaultValue : value;
    }
}
