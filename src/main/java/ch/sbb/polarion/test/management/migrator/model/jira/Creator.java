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
        "name",
        "key",
        "emailAddress",
        "avatarUrls",
        "displayName",
        "active",
        "timeZone"
})
public class Creator {

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
