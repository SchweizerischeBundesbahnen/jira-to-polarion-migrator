package ch.sbb.polarion.test.management.migrator.connector.jira.jql;

import ch.sbb.polarion.test.management.migrator.config.JiraQueryType;
import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import ch.sbb.polarion.test.management.migrator.exception.InvalidMigratorConfigurationException;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@AllArgsConstructor
public class JqlBuilder {
    public static final String START = "(";
    public static final String END = ")";
    public static final String KEY = "key=";
    public static final String OR = " OR ";
    private MigratorConfig migratorConfig;

    public String build() {
        JiraQueryType jiraQueryType = migratorConfig.getJiraQueryType();

        if (Objects.requireNonNull(jiraQueryType) == JiraQueryType.JQL) {
            return migratorConfig.getJiraQueryJql();
        } else if (jiraQueryType == JiraQueryType.KEYS) {
            String jiraQueryKeys = migratorConfig.getJiraQueryKeys();
            return toJql(jiraQueryKeys);
        }

        throw new InvalidMigratorConfigurationException("");
    }

    private String toJql(String jiraQueryKeys) {
        if (jiraQueryKeys == null || jiraQueryKeys.trim().isEmpty()) {
            throw new InvalidMigratorConfigurationException("jira.query.keys is empty");
        }

        List<String> issues = Arrays.stream(jiraQueryKeys.split(","))
                .map(String::trim)
                .toList();
        if (issues.isEmpty()) {
            throw new InvalidMigratorConfigurationException("jira.query.keys is not in CSV format");
        }

        StringBuilder builder = new StringBuilder();
        builder.append(START);

        boolean firstElement = true;
        for (int i = 0; i < issues.size(); i++) {
            String issue = issues.get(i);

            if (issue.isEmpty()) {
                continue;
            }

            if (!firstElement) {
                builder.append(OR);
            }
            builder.append(KEY);
            builder.append(issue);

            firstElement = false;
        }

        builder.append(END);

        return builder.toString();
    }
}
