package ch.sbb.polarion.test.management.migrator.model.jira;

import ch.sbb.polarion.test.management.migrator.model.CommonProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "self",
        "id",
        "key",
        "name",
        "projectTypeKey",
        "avatarUrls"
})
public class Project extends CommonProperties {

    @JsonProperty("self")
    public String self;
    @JsonProperty("id")
    public String id;
    @JsonProperty("key")
    public String key;
    @JsonProperty("name")
    public String name;
    @JsonProperty("projectTypeKey")
    public String projectTypeKey;
    @JsonProperty("avatarUrls")
    public AvatarUrls avatarUrls;
}
