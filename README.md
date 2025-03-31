[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=SchweizerischeBundesbahnen_open-source-polarion-java-repo-template&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=SchweizerischeBundesbahnen_open-source-polarion-java-repo-template)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=SchweizerischeBundesbahnen_open-source-polarion-java-repo-template&metric=bugs)](https://sonarcloud.io/summary/new_code?id=SchweizerischeBundesbahnen_open-source-polarion-java-repo-template)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=SchweizerischeBundesbahnen_open-source-polarion-java-repo-template&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=SchweizerischeBundesbahnen_open-source-polarion-java-repo-template)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=SchweizerischeBundesbahnen_open-source-polarion-java-repo-template&metric=coverage)](https://sonarcloud.io/summary/new_code?id=SchweizerischeBundesbahnen_open-source-polarion-java-repo-template)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=SchweizerischeBundesbahnen_open-source-polarion-java-repo-template&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=SchweizerischeBundesbahnen_open-source-polarion-java-repo-template)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=SchweizerischeBundesbahnen_open-source-polarion-java-repo-template&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=SchweizerischeBundesbahnen_open-source-polarion-java-repo-template)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=SchweizerischeBundesbahnen_open-source-polarion-java-repo-template&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=SchweizerischeBundesbahnen_open-source-polarion-java-repo-template)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=SchweizerischeBundesbahnen_open-source-polarion-java-repo-template&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=SchweizerischeBundesbahnen_open-source-polarion-java-repo-template)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=SchweizerischeBundesbahnen_open-source-polarion-java-repo-template&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=SchweizerischeBundesbahnen_open-source-polarion-java-repo-template)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=SchweizerischeBundesbahnen_open-source-polarion-java-repo-template&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=SchweizerischeBundesbahnen_open-source-polarion-java-repo-template)

# Jira to Polarion Migrator

This CLI tool is designed for migration Jira cucumber test cases to Polarion.

## Build

Jira to Polarion Migrator can be produced using maven:
```
mvn clean package
```

## Usage

Just run `java -jar jira-to-polarion-migrator-<version>-jar-with-dependencies.jar`.\
The migrator will check provided `jira-to-polarion-migrator.properties` config file and if everything ok start migration process.

## Configuration

`jira-to-polarion-migrator.properties` supports following properties:

| Property                                       | Description                                                                             |
|------------------------------------------------|-----------------------------------------------------------------------------------------|
| jira.base.url                                  | URL to Jira instance (e.g. https://jira.example.com)                                    |
| jira.security.type                             | Type of authentication for Jira: now `basic`, `bearer` and `oauth` are supported        |
| jira.security.username                         | Username if `basic` auth was selected                                                   |
| jira.security.password                         | Password if `basic` auth was selected                                                   |
| jira.security.personal.access.token            | Jira Personal Access Token if `bearer` auth was selected                                |
| jira.security.oauth.token.request.url          | OAuth2 Token URL if `oauth` auth was selected                                           |
| jira.security.oauth.client_id                  | OAuth2 Client ID if `oauth` auth was selected                                           |
| jira.security.oauth.client_secret              | OAuth2 Client Secret if `oauth` auth was selected                                       |
| jira.query.type                                | Type of Jira query to be used: `jql` or `keys`                                          |
| jira.query.jql                                 | Jira JQL query (e.g. `project = XRAYPRJ AND type = Test AND component = TestComponent`) |
| jira.query.keys                                | Comma separated Jira issues list (e.g. `XRAYPRJ-1111,XRAYPRJ-1112,XRAYPRJ-1113`)        |
| polarion.base.url                              | URL to target Polarion instance (e.g. `https://polarion.example.com`)                   |
| polarion.security.access.token                 | Polarion personal access token for REST services (Polarion REST should be enabled)      |
| polarion.target.project                        | Polarion Project ID (e.g. `XRAYPRJ`)                                                    |
| polarion.test.case.type                        | Polarion test case type (default `testcase`)                                            |
| polarion.test.case.testtype                    | Polarion test case testtype (default `TEST_TESTTYPE`)                                   |
| polarion.test.case.status                      | Polarion test case status (default `Draft`)                                             |
| polarion.test.case.severity                    | Polarion test case severity (default `normal`)                                          |
| polarion.test.case.custom.field.jira.issue.id  | Name of custom field to store Jira issue ID (e.g. `jiraIssueID`)                        |
| polarion.test.case.custom.field.jira.issue.url | Name of custom field to store URL to Jira issue (e.g. `jiraIssueURL`)                   |
