package ch.sbb.polarion.test.management.migrator;

import ch.sbb.polarion.test.management.migrator.connector.polarion.PolarionConnector;
import ch.sbb.polarion.test.management.migrator.model.jira.Fields;
import ch.sbb.polarion.test.management.migrator.model.jira.Issue;
import ch.sbb.polarion.test.management.migrator.model.jira.JiraIssues;
import ch.sbb.polarion.test.management.migrator.model.jira.RenderedFields;
import ch.sbb.polarion.test.management.migrator.model.polarion.WorkItem;
import ch.sbb.polarion.test.management.migrator.model.polarion.WorkItems;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MigratorTest {
    @Test
    void testProcessJiraIssues() {
        // Arrange
        Issue issue1 = new Issue();
        issue1.setKey("JIRA-123");
        issue1.setRenderedFields(new RenderedFields());
        issue1.setFields(new Fields());
        Issue issue2 = new Issue();
        issue2.setKey("JIRA-456");
        issue2.setRenderedFields(new RenderedFields());
        issue2.setFields(new Fields());
        JiraIssues jiraIssues = new JiraIssues();
        jiraIssues.setIssues(List.of(issue1, issue2));


        // Mock WorkItem results
        WorkItem wi1 = mock(WorkItem.class);
        WorkItem wi2 = mock(WorkItem.class);
        when(wi1.getId()).thenReturn("WI-123");
        when(wi2.getId()).thenReturn("WI-456");

        WorkItems workItems = new WorkItems();
        workItems.setData(List.of(wi1, wi2));

        PolarionConnector connector = mock(PolarionConnector.class);
        when(connector.importWorkItems(any())).thenReturn(workItems);

        // Act
        Map<String, String> result = Migrator.processJiraIssues(List.of(jiraIssues), connector);

        // Assert
        assertEquals(2, result.size());
        assertEquals("WI-123", result.get("JIRA-123"));
        assertEquals("WI-456", result.get("JIRA-456"));
    }
}
