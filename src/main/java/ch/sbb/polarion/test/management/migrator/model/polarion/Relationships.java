package ch.sbb.polarion.test.management.migrator.model.polarion;

import ch.sbb.polarion.test.management.migrator.model.CommonProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

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
public class Relationships extends CommonProperties {
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
}
