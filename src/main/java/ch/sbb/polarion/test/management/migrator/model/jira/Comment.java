package ch.sbb.polarion.test.management.migrator.model.jira;

import ch.sbb.polarion.test.management.migrator.model.CommonProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "comments",
        "maxResults",
        "total",
        "startAt"
})
public class Comment extends CommonProperties {

    @JsonProperty("comments")
    public List<Object> comments = null;
    @JsonProperty("maxResults")
    public Long maxResults;
    @JsonProperty("total")
    public Long total;
    @JsonProperty("startAt")
    public Long startAt;
}
