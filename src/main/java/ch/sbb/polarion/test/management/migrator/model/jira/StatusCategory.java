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
        "colorName",
        "name"
})
public class StatusCategory extends CommonProperties {

    @JsonProperty("self")
    public String self;
    @JsonProperty("id")
    public Long id;
    @JsonProperty("key")
    public String key;
    @JsonProperty("colorName")
    public String colorName;
    @JsonProperty("name")
    public String name;
}
