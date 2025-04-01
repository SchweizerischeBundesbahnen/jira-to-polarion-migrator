package ch.sbb.polarion.test.management.migrator.model.jira;

import ch.sbb.polarion.test.management.migrator.model.CommonProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "startAt",
        "maxResults",
        "total",
        "worklogs"
})
public class Worklog extends CommonProperties {

    @JsonProperty("startAt")
    public Long startAt;
    @JsonProperty("maxResults")
    public Long maxResults;
    @JsonProperty("total")
    public Long total;
    @JsonProperty("worklogs")
    public List<WorklogEntry> worklogs = null;
}
