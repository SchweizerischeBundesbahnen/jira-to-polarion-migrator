package ch.sbb.polarion.test.management.migrator.model.jira;

import ch.sbb.polarion.test.management.migrator.model.CommonProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "progress",
        "total",
        "percent"
})
public class Aggregateprogress extends CommonProperties {

    @JsonProperty("progress")
    public Long progress;
    @JsonProperty("total")
    public Long total;
    @JsonProperty("percent")
    public Long percent;
}
