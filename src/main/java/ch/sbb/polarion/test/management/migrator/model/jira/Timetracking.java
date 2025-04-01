package ch.sbb.polarion.test.management.migrator.model.jira;

import ch.sbb.polarion.test.management.migrator.model.CommonProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "timeSpent",
        "timeSpentSeconds"
})
public class Timetracking extends CommonProperties {

    @JsonProperty("timeSpent")
    public String timeSpent;
    @JsonProperty("timeSpentSeconds")
    public Long timeSpentSeconds;
}
