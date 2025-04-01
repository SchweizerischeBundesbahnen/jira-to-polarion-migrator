package ch.sbb.polarion.test.management.migrator.model.jira;

import ch.sbb.polarion.test.management.migrator.model.CommonProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "self",
        "watchCount",
        "isWatching"
})
public class Watches extends CommonProperties {

    @JsonProperty("self")
    public String self;
    @JsonProperty("watchCount")
    public Long watchCount;
    @JsonProperty("isWatching")
    public Boolean isWatching;
}
