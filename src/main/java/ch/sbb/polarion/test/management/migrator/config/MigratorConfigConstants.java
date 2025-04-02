package ch.sbb.polarion.test.management.migrator.config;

public interface MigratorConfigConstants {
    String JIRA_TO_POLARION_MIGRATOR_PROPERTIES = "jira-to-polarion-migrator.properties";

    String JIRA_BASE_URL = "jira.base.url";

    String JIRA_SECURITY_TYPE = "jira.security.type";

    String JIRA_SECURITY_USERNAME = "jira.security.username";
    String JIRA_SECURITY_PASSWORD = "jira.security.password";
    String JIRA_SECURITY_PERSONAL_ACCESS_TOKEN = "jira.security.personal.access.token";

    String JIRA_SECURITY_OAUTH_TOKEN_REQUEST_URL = "jira.security.oauth.token.request.url";
    String JIRA_SECURITY_OAUTH_CLIENT_ID = "jira.security.oauth.client_id";
    String JIRA_SECURITY_OAUTH_CLIENT_SECRET = "jira.security.oauth.client_secret";

    String JIRA_QUERY_TYPE = "jira.query.type";

    String JIRA_QUERY_JQL = "jira.query.jql";
    String JIRA_QUERY_KEYS = "jira.query.keys";

    String POLARION_TEST_CASE_TYPE = "polarion.test.case.type";
    String POLARION_TEST_CASE_TESTTYPE = "polarion.test.case.testtype";
    String POLARION_TEST_CASE_STATUS = "polarion.test.case.status";
    String POLARION_TEST_CASE_SEVERITY = "polarion.test.case.severity";

    String POLARION_TEST_CASE_CUSTOM_FIELD_JIRA_ISSUE_ID = "polarion.test.case.custom.field.jira.issue.id";
    String POLARION_TEST_CASE_CUSTOM_FIELD_JIRA_ISSUE_URL = "polarion.test.case.custom.field.jira.issue.url";

    String POLARION_BASE_URL = "polarion.base.url";
    String POLARION_SECURITY_ACCESS_TOKEN = "polarion.security.access.token";

    String POLARION_TARGET_PROJECT = "polarion.target.project";
}
