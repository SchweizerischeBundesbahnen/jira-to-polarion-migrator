package ch.sbb.polarion.test.management.migrator.model.polarion;


import ch.sbb.polarion.test.management.migrator.model.CommonProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "type",
        "testType",
        "title",
        "description",
        "severity",
        "priority",
        "status",
        "created",
        "updated",
        "occurredInVersion"
})
@Data
public class Attributes extends CommonProperties {

    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("testType")
    private String testType;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private Description description;
    @JsonProperty("severity")
    private String severity;
    @JsonProperty("priority")
    private String priority;
    @JsonProperty("status")
    private String status;
    @JsonProperty("created")
    private String created;
    @JsonProperty("updated")
    private String updated;
    @JsonProperty("occurredInVersion")
    private String occurredInVersion;
}
