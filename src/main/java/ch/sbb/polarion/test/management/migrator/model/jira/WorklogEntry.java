package ch.sbb.polarion.test.management.migrator.model.jira;

import ch.sbb.polarion.test.management.migrator.model.CommonProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
public class WorklogEntry extends CommonProperties {

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
}
