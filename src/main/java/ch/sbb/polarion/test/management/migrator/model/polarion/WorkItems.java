package ch.sbb.polarion.test.management.migrator.model.polarion;

import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import ch.sbb.polarion.test.management.migrator.model.jira.Issue;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "data"
})
@Data
public class WorkItems {

    public static final Map<String, String> PRIORITY_MAP = Map.of(
            "Trivial", "20",
            "Minor", "40",
            "Major", "60",
            "Critical", "80",
            "Blocker", "100"
    );

    @JsonProperty("data")
    private List<WorkItem> data = new ArrayList<>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public void fromJiraIssues(List<Issue> issues) {
        for (Issue issue : issues) {
            WorkItem workItem = new WorkItem();

            Description description = new Description();
            description.setValue(issue.renderedFields.getDescription());
            description.setType("text/html");

            workItem.getAttributes().setTitle(issue.fields.summary);
            workItem.getAttributes().setDescription(description);
            workItem.getAttributes().setType(MigratorConfig.getInstance().getPolarionTestCaseType());
            workItem.getAttributes().setTestType(MigratorConfig.getInstance().getPolarionTestCaseTesttype());
            workItem.getAttributes().setSeverity(MigratorConfig.getInstance().getPolarionTestCaseSeverity());
            if (issue.fields.priority != null && PRIORITY_MAP.containsKey(issue.fields.priority.name)) {
                workItem.getAttributes().setPriority(PRIORITY_MAP.get(issue.fields.priority.name));
            }
            workItem.getAttributes().setStatus(MigratorConfig.getInstance().getPolarionTestCaseStatus());

            String polarionTestCaseCustomFieldJiraIssueId = MigratorConfig.getInstance().getPolarionTestCaseCustomFieldJiraIssueId();
            if (polarionTestCaseCustomFieldJiraIssueId != null) {
                workItem.getAttributes().setAdditionalProperty(polarionTestCaseCustomFieldJiraIssueId, issue.key);
            }
            String polarionTestCaseCustomFieldJiraIssueUrl = MigratorConfig.getInstance().getPolarionTestCaseCustomFieldJiraIssueUrl();
            if (polarionTestCaseCustomFieldJiraIssueUrl != null) {
                workItem.getAttributes().setAdditionalProperty(polarionTestCaseCustomFieldJiraIssueUrl, MigratorConfig.getInstance().getJiraBaseUrl() + "/browse/" + issue.key);
            }

            data.add(workItem);
        }
    }

}
