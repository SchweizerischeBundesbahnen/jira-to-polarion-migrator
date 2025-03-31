package ch.sbb.polarion.test.management.migrator.model.polarion;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "project",
        "linkedWorkItems",
        "comments",
        "attachments",
        "categories",
        "author",
        "assignee"
})
@Data
public class Relationships {

    @JsonProperty("project")
    private Project project;
    @JsonProperty("linkedWorkItems")
    private LinkedWorkItems linkedWorkItems;
    @JsonProperty("comments")
    private Comments comments;
    @JsonProperty("attachments")
    private Attachments attachments;
    @JsonProperty("categories")
    private Categories categories;
    @JsonProperty("author")
    private Author author;
    @JsonProperty("assignee")
    private Assignee assignee;
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
