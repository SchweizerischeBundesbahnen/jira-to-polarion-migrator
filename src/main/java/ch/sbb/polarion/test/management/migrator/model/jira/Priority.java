package ch.sbb.polarion.test.management.migrator.model.jira;

import ch.sbb.polarion.test.management.migrator.model.CommonProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "self",
        "iconUrl",
        "name",
        "id"
})
public class Priority extends CommonProperties {

    @JsonProperty("self")
    public String self;
    @JsonProperty("iconUrl")
    public String iconUrl;
    @JsonProperty("name")
    public String name;
    @JsonProperty("id")
    public String id;
}
