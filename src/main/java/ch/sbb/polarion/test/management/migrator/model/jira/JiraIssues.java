package ch.sbb.polarion.test.management.migrator.model.jira;

import ch.sbb.polarion.test.management.migrator.model.CommonProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "expand",
        "startAt",
        "maxResults",
        "total",
        "issues"
})
@Data
public class JiraIssues extends CommonProperties {

    @JsonProperty("expand")
    public String expand;
    @JsonProperty("startAt")
    public Long startAt;
    @JsonProperty("maxResults")
    public Long maxResults;
    @JsonProperty("total")
    public Long total;
    @JsonProperty("issues")
    public List<Issue> issues = null;
}
