package ch.sbb.polarion.test.management.migrator.model.jira;

import ch.sbb.polarion.test.management.migrator.model.CommonProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "issuetype",
        "timespent",
        "project",
        "fixVersions",
        "customfield_11520",
        "aggregatetimespent",
        "resolution",
        "customfield_10621",
        "customfield_10622",
        "customfield_10820",
        "customfield_10623",
        "customfield_10821",
        "customfield_10624",
        "customfield_10625",
        "resolutiondate",
        "workratio",
        "lastViewed",
        "watches",
        "created",
        "customfield_12120",
        "customfield_10220",
        "priority",
        "customfield_12520",
        "labels",
        "customfield_10620",
        "customfield_11425",
        "customfield_11424",
        "customfield_11823",
        "customfield_11822",
        "timeestimate",
        "aggregatetimeoriginalestimate",
        "versions",
        "issuelinks",
        "assignee",
        "updated",
        "status",
        "components",
        "customfield_13120",
        "timeoriginalestimate",
        "customfield_11020",
        "description",
        "customfield_10010",
        "customfield_11220",
        "customfield_10011",
        "customfield_11221",
        "customfield_11421",
        "customfield_11222",
        "customfield_10013",
        "customfield_11423",
        "customfield_11422",
        "customfield_11620",
        "customfield_10920",
        "customfield_10525",
        "aggregatetimeestimate",
        "summary",
        "creator",
        "subtasks",
        "customfield_12220",
        "reporter",
        "customfield_12222",
        "customfield_12420",
        "customfield_10000",
        "customfield_12221",
        "aggregateprogress",
        "customfield_10001",
        "customfield_12224",
        "customfield_10320",
        "customfield_10004",
        "customfield_10521",
        "environment",
        "duedate",
        "progress"
})
public class RenderedFields extends CommonProperties {

    @JsonProperty("issuetype")
    private Object issuetype;
    @JsonProperty("timespent")
    private String timespent;
    @JsonProperty("project")
    private Object project;
    @JsonProperty("fixVersions")
    private Object fixVersions;
    @JsonProperty("customfield_11520")
    private String customfield11520;
    @JsonProperty("aggregatetimespent")
    private String aggregatetimespent;
    @JsonProperty("resolution")
    private Object resolution;
    @JsonProperty("customfield_10621")
    private Object customfield10621;
    @JsonProperty("customfield_10622")
    private Object customfield10622;
    @JsonProperty("customfield_10820")
    private Object customfield10820;
    @JsonProperty("customfield_10623")
    private Object customfield10623;
    @JsonProperty("customfield_10821")
    private Object customfield10821;
    @JsonProperty("customfield_10624")
    private Object customfield10624;
    @JsonProperty("customfield_10625")
    private Object customfield10625;
    @JsonProperty("resolutiondate")
    private Object resolutiondate;
    @JsonProperty("workratio")
    private Object workratio;
    @JsonProperty("lastViewed")
    private String lastViewed;
    @JsonProperty("watches")
    private Object watches;
    @JsonProperty("created")
    private String created;
    @JsonProperty("customfield_12120")
    private Object customfield12120;
    @JsonProperty("customfield_10220")
    private Object customfield10220;
    @JsonProperty("priority")
    private Object priority;
    @JsonProperty("customfield_12520")
    private Object customfield12520;
    @JsonProperty("labels")
    private Object labels;
    @JsonProperty("customfield_10620")
    private Object customfield10620;
    @JsonProperty("customfield_11425")
    private String customfield11425;
    @JsonProperty("customfield_11424")
    private String customfield11424;
    @JsonProperty("customfield_11823")
    private String customfield11823;
    @JsonProperty("customfield_11822")
    private Object customfield11822;
    @JsonProperty("timeestimate")
    private Object timeestimate;
    @JsonProperty("aggregatetimeoriginalestimate")
    private Object aggregatetimeoriginalestimate;
    @JsonProperty("versions")
    private Object versions;
    @JsonProperty("issuelinks")
    private Object issuelinks;
    @JsonProperty("assignee")
    private Object assignee;
    @JsonProperty("updated")
    private String updated;
    @JsonProperty("status")
    private Object status;
    @JsonProperty("components")
    private Object components;
    @JsonProperty("customfield_13120")
    private Object customfield13120;
    @JsonProperty("timeoriginalestimate")
    private Object timeoriginalestimate;
    @JsonProperty("customfield_11020")
    private Object customfield11020;
    @JsonProperty("description")
    private String description;
    @JsonProperty("customfield_10010")
    private Object customfield10010;
    @JsonProperty("customfield_11220")
    private Object customfield11220;
    @JsonProperty("customfield_10011")
    private String customfield10011;
    @JsonProperty("customfield_11221")
    private Object customfield11221;
    @JsonProperty("customfield_11421")
    private Object customfield11421;
    @JsonProperty("customfield_11222")
    private Object customfield11222;
    @JsonProperty("customfield_10013")
    private Object customfield10013;
    @JsonProperty("customfield_11423")
    private String customfield11423;
    @JsonProperty("customfield_11422")
    private String customfield11422;
    @JsonProperty("customfield_11620")
    private String customfield11620;
    @JsonProperty("customfield_10920")
    private Object customfield10920;
    @JsonProperty("customfield_10525")
    private String customfield10525;
    @JsonProperty("aggregatetimeestimate")
    private Object aggregatetimeestimate;
    @JsonProperty("summary")
    private Object summary;
    @JsonProperty("creator")
    private Object creator;
    @JsonProperty("subtasks")
    private Object subtasks;
    @JsonProperty("customfield_12220")
    private Object customfield12220;
    @JsonProperty("reporter")
    private Object reporter;
    @JsonProperty("customfield_12222")
    private Object customfield12222;
    @JsonProperty("customfield_12420")
    private String customfield12420;
    @JsonProperty("customfield_10000")
    private Object customfield10000;
    @JsonProperty("customfield_12221")
    private Object customfield12221;
    @JsonProperty("aggregateprogress")
    private Object aggregateprogress;
    @JsonProperty("customfield_10001")
    private Object customfield10001;
    @JsonProperty("customfield_12224")
    private Object customfield12224;
    @JsonProperty("customfield_10320")
    private Object customfield10320;
    @JsonProperty("customfield_10004")
    private Object customfield10004;
    @JsonProperty("customfield_10521")
    private Object customfield10521;
    @JsonProperty("environment")
    private String environment;
    @JsonProperty("duedate")
    private Object duedate;
    @JsonProperty("progress")
    private Object progress;
}
