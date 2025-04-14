package ch.sbb.polarion.test.management.migrator.model.polarion;

import ch.sbb.polarion.test.management.migrator.model.CommonProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "id",
        "attributes",
        "links"
})
@Getter
@Setter
public class WorkItem extends CommonProperties {
    @JsonProperty("type")
    private String type = "workitems";
    @JsonProperty("id")
    private String id;
    @JsonProperty("attributes")
    private Attributes attributes = new Attributes();
    @JsonProperty("links")
    private Links links;
}
