package ch.sbb.polarion.test.management.migrator.model.jira;

import ch.sbb.polarion.test.management.migrator.model.CommonProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "expand",
        "id",
        "self",
        "key",
        "fields",
        "renderedFields"
})
@Data
public class Issue extends CommonProperties {

    @JsonProperty("expand")
    public String expand;
    @JsonProperty("id")
    public String id;
    @JsonProperty("self")
    public String self;
    @JsonProperty("key")
    public String key;
    @JsonProperty("fields")
    public Fields fields;
    @JsonProperty("renderedFields")
    public RenderedFields renderedFields;
}
