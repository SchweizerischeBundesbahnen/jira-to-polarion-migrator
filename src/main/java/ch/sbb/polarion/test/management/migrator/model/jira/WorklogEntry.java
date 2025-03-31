package ch.sbb.polarion.test.management.migrator.model.jira;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "self",
        "author",
        "updateAuthor",
        "comment",
        "created",
        "updated",
        "started",
        "timeSpent",
        "timeSpentSeconds",
        "id",
        "issueId"
})
public class WorklogEntry {

    @JsonProperty("self")
    public String self;
    @JsonProperty("author")
    public Author author;
    @JsonProperty("updateAuthor")
    public UpdateAuthor updateAuthor;
    @JsonProperty("comment")
    public String comment;
    @JsonProperty("created")
    public String created;
    @JsonProperty("updated")
    public String updated;
    @JsonProperty("started")
    public String started;
    @JsonProperty("timeSpent")
    public String timeSpent;
    @JsonProperty("timeSpentSeconds")
    public Long timeSpentSeconds;
    @JsonProperty("id")
    public String id;
    @JsonProperty("issueId")
    public String issueId;
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

}
