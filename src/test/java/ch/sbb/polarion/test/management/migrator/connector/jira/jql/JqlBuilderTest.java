package ch.sbb.polarion.test.management.migrator.connector.jira.jql;

import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Properties;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class JqlBuilderTest {

    private static Stream<Arguments> testValuesForJqlBuilderBuildMethod() {
        return Stream.of(
                Arguments.of("not validated JQL query", null, "not validated JQL query"),
                Arguments.of("(key=TESTPRJ-16)", null, "(key=TESTPRJ-16)"),
                Arguments.of(null, "TESTPRJ-17", "(key=TESTPRJ-17)"),
                Arguments.of(null, " ,  , TESTPRJ-17,  ,  ", "(key=TESTPRJ-17)"),
                Arguments.of(null, "TESTPRJ-17,TESTPRJ-16,TESTPRJ-15", "(key=TESTPRJ-17 OR key=TESTPRJ-16 OR key=TESTPRJ-15)"),
                Arguments.of(null, "TESTPRJ-17   ,  TESTPRJ-15,  TESTPRJ-16", "(key=TESTPRJ-17 OR key=TESTPRJ-15 OR key=TESTPRJ-16)")
        );
    }

    @ParameterizedTest
    @MethodSource("testValuesForJqlBuilderBuildMethod")
    void testJqlBuilderBuildMethod(String jiraJqlQuery, String jiraIssueKeys, String expected) {
        Properties properties = new Properties();
        if (jiraJqlQuery != null) {
            properties.setProperty("jira.query.type", "jql");
            properties.setProperty("jira.query.jql", jiraJqlQuery);
        } else if (jiraIssueKeys != null) {
            properties.setProperty("jira.query.type", "keys");
            properties.setProperty("jira.query.keys", jiraIssueKeys);
        }
        MigratorConfig migratorConfig = new MigratorConfig();
        migratorConfig.setProperties(properties);

        String jqlQuery = new JqlBuilder(migratorConfig).build();

        assertEquals(expected, jqlQuery);
    }
}
