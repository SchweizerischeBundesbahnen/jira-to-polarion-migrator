package ch.sbb.polarion.test.management.migrator.model.polarion;

import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import ch.sbb.polarion.test.management.migrator.model.CommonProperties;
import ch.sbb.polarion.test.management.migrator.model.jira.Issue;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "data"
})
@Data
public class WorkItems extends CommonProperties {

    public static final Map<String, String> PRIORITY_MAP = Map.of(
            "Trivial", "20",
            "Minor", "40",
            "Major", "60",
            "Critical", "80",
            "Blocker", "100"
    );

    @JsonProperty("data")
    private List<WorkItem> data = new ArrayList<>();

    public void fromJiraIssues(List<Issue> issues) {
        for (Issue issue : issues) {
            WorkItem workItem = new WorkItem();

            Description description = new Description();
            description.setValue(issue.renderedFields.getDescription());
            description.setType("text/html");

            workItem.getAttributes().setTitle(issue.fields.summary);
            workItem.getAttributes().setDescription(description);
            workItem.getAttributes().setType(MigratorConfig.getPolarionTestCaseType());
            workItem.getAttributes().setTestType(MigratorConfig.getPolarionTestCaseTesttype());
            workItem.getAttributes().setSeverity(MigratorConfig.getPolarionTestCaseSeverity());
            if (issue.fields.priority != null && PRIORITY_MAP.containsKey(issue.fields.priority.name)) {
                workItem.getAttributes().setPriority(PRIORITY_MAP.get(issue.fields.priority.name));
            }
            workItem.getAttributes().setStatus(MigratorConfig.getPolarionTestCaseStatus());

            String polarionTestCaseCustomFieldJiraIssueId = MigratorConfig.getPolarionTestCaseCustomFieldJiraIssueId();
            if (polarionTestCaseCustomFieldJiraIssueId != null) {
                workItem.getAttributes().setAdditionalProperty(polarionTestCaseCustomFieldJiraIssueId, issue.key);
            }
            String polarionTestCaseCustomFieldJiraIssueUrl = MigratorConfig.getPolarionTestCaseCustomFieldJiraIssueUrl();
            if (polarionTestCaseCustomFieldJiraIssueUrl != null) {
                workItem.getAttributes().setAdditionalProperty(polarionTestCaseCustomFieldJiraIssueUrl, MigratorConfig.getJiraBaseUrl() + "/browse/" + issue.key);
            }

            data.add(workItem);
        }
    }

}
