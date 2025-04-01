package ch.sbb.polarion.test.management.migrator.model.jira;

import ch.sbb.polarion.test.management.migrator.model.CommonProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "48x48",
        "24x24",
        "16x16",
        "32x32"
})
public class AvatarUrls extends CommonProperties {

    @JsonProperty("48x48")
    public String image48x48;
    @JsonProperty("24x24")
    public String image24x24;
    @JsonProperty("16x16")
    public String image16x16;
    @JsonProperty("32x32")
    public String image32x32;
}
