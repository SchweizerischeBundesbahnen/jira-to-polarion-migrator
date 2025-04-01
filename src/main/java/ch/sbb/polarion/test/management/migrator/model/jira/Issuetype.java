package ch.sbb.polarion.test.management.migrator.model.jira;

import ch.sbb.polarion.test.management.migrator.model.CommonProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "self",
        "id",
        "description",
        "iconUrl",
        "name",
        "subtask",
        "avatarId"
})
public class Issuetype extends CommonProperties {

    @JsonProperty("self")
    public String self;
    @JsonProperty("id")
    public String id;
    @JsonProperty("description")
    public String description;
    @JsonProperty("iconUrl")
    public String iconUrl;
    @JsonProperty("name")
    public String name;
    @JsonProperty("subtask")
    public Boolean subtask;
    @JsonProperty("avatarId")
    public Long avatarId;
}
