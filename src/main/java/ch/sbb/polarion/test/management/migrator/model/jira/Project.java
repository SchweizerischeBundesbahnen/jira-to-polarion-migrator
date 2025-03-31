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
        "id",
        "key",
        "name",
        "projectTypeKey",
        "avatarUrls"
})
public class Project {

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
