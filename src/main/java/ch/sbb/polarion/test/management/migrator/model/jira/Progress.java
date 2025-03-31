package ch.sbb.polarion.test.management.migrator.model.jira;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("java:S1700")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "progress",
        "total",
        "percent"
})
public class Progress {

    @JsonProperty("progress")
    public Long progress;
    @JsonProperty("total")
    public Long total;
    @JsonProperty("percent")
    public Long percent;
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
