package ch.sbb.polarion.test.management.migrator.model.polarion;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DescriptionTest {
    @Test
    void testSerialization() throws Exception {
        Description desc = new Description();
        desc.setType("summary");
        desc.setValue("description");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(desc);

        assertTrue(json.contains("summary"));
        assertTrue(json.contains("description"));
    }
}
