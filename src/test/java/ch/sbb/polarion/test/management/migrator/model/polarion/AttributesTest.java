package ch.sbb.polarion.test.management.migrator.model.polarion;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AttributesTest {
    @Test
    @SneakyThrows
    void testAttributesSerialization() {
        ObjectMapper mapper = new ObjectMapper();

        Description desc = new Description();
        desc.setType("summary");
        desc.setValue("Test description value");

        Attributes attr = new Attributes();
        attr.setId("1234");
        attr.setType("bug");
        attr.setTestType("functional");
        attr.setTitle("Sample bug report");
        attr.setDescription(desc);
        attr.setSeverity("high");
        attr.setPriority("P1");
        attr.setStatus("open");
        attr.setCreated("2025-04-07T12:00:00Z");
        attr.setUpdated("2025-04-07T14:00:00Z");
        attr.setOccurredInVersion("1.0.0");

        String json = mapper.writeValueAsString(attr);

        assertTrue(json.contains("Sample bug report"));
        assertTrue(json.contains("Test description value"));
        assertTrue(json.contains("occurredInVersion"));
    }

    @Test
    void testAttributesDeserialization() throws Exception {
        String json = """
                {
                  "id": "1234",
                  "type": "bug",
                  "testType": "functional",
                  "title": "Sample bug report",
                  "description": {
                    "type": "summary",
                    "value": "Test description value"
                  },
                  "severity": "high",
                  "priority": "P1",
                  "status": "open",
                  "created": "2025-04-07T12:00:00Z",
                  "updated": "2025-04-07T14:00:00Z",
                  "occurredInVersion": "1.0.0"
                }
                """;

        ObjectMapper mapper = new ObjectMapper();
        Attributes attr = mapper.readValue(json, Attributes.class);

        assertEquals("Sample bug report", attr.getTitle());
        assertEquals("summary", attr.getDescription().getType());
    }
}
