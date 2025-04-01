package ch.sbb.polarion.test.management.migrator.model.jira;

import ch.sbb.polarion.test.management.migrator.model.CommonProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

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
        "timetracking",
        "customfield_11422",
        "customfield_11620",
        "customfield_10920",
        "customfield_10525",
        "attachment",
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
        "progress",
        "comment",
        "worklog"
})
public class Fields extends CommonProperties {

    @JsonProperty("issuetype")
    public Issuetype issuetype;
    @JsonProperty("timespent")
    public Long timespent;
    @JsonProperty("project")
    public Project project;
    @JsonProperty("fixVersions")
    public List<Object> fixVersions = null;
    @JsonProperty("customfield_11520")
    public Object customfield11520;
    @JsonProperty("aggregatetimespent")
    public Long aggregatetimespent;
    @JsonProperty("resolution")
    public Object resolution;
    @JsonProperty("customfield_10621")
    public Object customfield10621;
    @JsonProperty("customfield_10622")
    public Object customfield10622;
    @JsonProperty("customfield_10820")
    public Object customfield10820;
    @JsonProperty("customfield_10623")
    public Object customfield10623;
    @JsonProperty("customfield_10821")
    public Object customfield10821;
    @JsonProperty("customfield_10624")
    public Object customfield10624;
    @JsonProperty("customfield_10625")
    public Object customfield10625;
    @JsonProperty("resolutiondate")
    public Object resolutiondate;
    @JsonProperty("workratio")
    public Long workratio;
    @JsonProperty("lastViewed")
    public Object lastViewed;
    @JsonProperty("watches")
    public Watches watches;
    @JsonProperty("created")
    public String created;
    @JsonProperty("customfield_12120")
    public Object customfield12120;
    @JsonProperty("customfield_10220")
    public String customfield10220;
    @JsonProperty("priority")
    public Priority priority;
    @JsonProperty("customfield_12520")
    public Object customfield12520;
    @JsonProperty("labels")
    public List<Object> labels = null;
    @JsonProperty("customfield_10620")
    public Object customfield10620;
    @JsonProperty("customfield_11425")
    public Object customfield11425;
    @JsonProperty("customfield_11424")
    public Object customfield11424;
    @JsonProperty("customfield_11823")
    public Object customfield11823;
    @JsonProperty("customfield_11822")
    public Object customfield11822;
    @JsonProperty("timeestimate")
    public Object timeestimate;
    @JsonProperty("aggregatetimeoriginalestimate")
    public Object aggregatetimeoriginalestimate;
    @JsonProperty("versions")
    public List<Object> versions = null;
    @JsonProperty("issuelinks")
    public List<Object> issuelinks = null;
    @JsonProperty("assignee")
    public Object assignee;
    @JsonProperty("updated")
    public String updated;
    @JsonProperty("status")
    public Status status;
    @JsonProperty("components")
    public List<Object> components = null;
    @JsonProperty("customfield_13120")
    public String customfield13120;
    @JsonProperty("timeoriginalestimate")
    public Object timeoriginalestimate;
    @JsonProperty("customfield_11020")
    public String customfield11020;
    @JsonProperty("description")
    public String description;
    @JsonProperty("customfield_10010")
    public Object customfield10010;
    @JsonProperty("customfield_11220")
    public Object customfield11220;
    @JsonProperty("customfield_10011")
    public Object customfield10011;
    @JsonProperty("customfield_11221")
    public Object customfield11221;
    @JsonProperty("customfield_11421")
    public Object customfield11421;
    @JsonProperty("customfield_11222")
    public Object customfield11222;
    @JsonProperty("customfield_10013")
    public Object customfield10013;
    @JsonProperty("customfield_11423")
    public Object customfield11423;
    @JsonProperty("timetracking")
    public Timetracking timetracking;
    @JsonProperty("customfield_11422")
    public Object customfield11422;
    @JsonProperty("customfield_11620")
    public Object customfield11620;
    @JsonProperty("customfield_10920")
    public Object customfield10920;
    @JsonProperty("customfield_10525")
    public Object customfield10525;
    @JsonProperty("attachment")
    public List<Object> attachment = null;
    @JsonProperty("aggregatetimeestimate")
    public Object aggregatetimeestimate;
    @JsonProperty("summary")
    public String summary;
    @JsonProperty("creator")
    public Creator creator;
    @JsonProperty("subtasks")
    public List<Object> subtasks = null;
    @JsonProperty("customfield_12220")
    public Double customfield12220;
    @JsonProperty("reporter")
    public Reporter reporter;
    @JsonProperty("customfield_12222")
    public List<Customfield12222> customfield12222 = null;
    @JsonProperty("customfield_12420")
    public Object customfield12420;
    @JsonProperty("customfield_10000")
    public Object customfield10000;
    @JsonProperty("customfield_12221")
    public Customfield12221 customfield12221;
    @JsonProperty("aggregateprogress")
    public Aggregateprogress aggregateprogress;
    @JsonProperty("customfield_10001")
    public Object customfield10001;
    @JsonProperty("customfield_12224")
    public Object customfield12224;
    @JsonProperty("customfield_10320")
    public String customfield10320;
    @JsonProperty("customfield_10004")
    public Object customfield10004;
    @JsonProperty("customfield_10521")
    public Object customfield10521;
    @JsonProperty("environment")
    public Object environment;
    @JsonProperty("duedate")
    public Object duedate;
    @JsonProperty("progress")
    public Progress progress;
    @JsonProperty("comment")
    public Comment comment;
    @JsonProperty("worklog")
    public Worklog worklog;
}
