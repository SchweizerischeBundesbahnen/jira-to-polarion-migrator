package ch.sbb.polarion.test.management.migrator.model.polarion;

import ch.sbb.polarion.test.management.migrator.model.CommonProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "self",
        "portal"
})
@Getter
@Setter
public class Links extends CommonProperties {
    @JsonProperty("self")
    private String self;
    @JsonProperty("portal")
    private String portal;
}
