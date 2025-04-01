package ch.sbb.polarion.test.management.migrator.model.jira;

import ch.sbb.polarion.test.management.migrator.model.CommonProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "self",
        "name",
        "key",
        "emailAddress",
        "avatarUrls",
        "displayName",
        "active",
        "timeZone"
})
public class Assignee extends CommonProperties {

    @JsonProperty("self")
    public String self;
    @JsonProperty("name")
    public String name;
    @JsonProperty("key")
    public String key;
    @JsonProperty("emailAddress")
    public String emailAddress;
    @JsonProperty("avatarUrls")
    public AvatarUrls avatarUrls;
    @JsonProperty("displayName")
    public String displayName;
    @JsonProperty("active")
    public Boolean active;
    @JsonProperty("timeZone")
    public String timeZone;
}
