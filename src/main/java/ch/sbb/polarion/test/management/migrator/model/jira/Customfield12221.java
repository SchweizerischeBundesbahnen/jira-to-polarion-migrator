package ch.sbb.polarion.test.management.migrator.model.jira;

import ch.sbb.polarion.test.management.migrator.model.CommonProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "self",
        "value",
        "id",
        "disabled"
})
public class Customfield12221 extends CommonProperties {

    @JsonProperty("self")
    public String self;
    @JsonProperty("value")
    public String value;
    @JsonProperty("id")
    public String id;
    @JsonProperty("disabled")
    public Boolean disabled;
}
