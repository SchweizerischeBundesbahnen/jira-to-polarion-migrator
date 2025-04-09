package ch.sbb.polarion.test.management.migrator.utils;

import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


class MappingFileUtilsTest {

    @TempDir
    Path tempDir;

    @Test
    void getMappingFileShouldReturnCorrectPath() {
        MigratorConfig config = new MigratorConfig() {
            @Override
            public String getConfigurationPath() {
                return tempDir.toString();
            }
        };

        String mappingFile = MappingFileUtils.getMappingFile(config);

        assertTrue(mappingFile.endsWith("jira-to-polarion.mapping"));
    }

    @Test
    @SneakyThrows
    void deleteMappingFile() {
        Path file = tempDir.resolve("jira-to-polarion.mapping");
        Files.createFile(file);

        MigratorConfig config = new MigratorConfig() {
            @Override
            public String getConfigurationPath() {
                return tempDir.toString();
            }
        };

        assertTrue(Files.exists(file));

        MappingFileUtils.deleteMappingFile(config);

        assertFalse(Files.exists(file), "File should be deleted");
    }

    @Test
    @SneakyThrows
    void testSaveMappingToFileWritesSortedFile() {
        Map<String, String> mapping = Map.of(
                "JIRA-2", "WI-002",
                "JIRA-1", "WI-001",
                "JIRA-3", "WI-003"
        );

        MigratorConfig config = new MigratorConfig() {
            @Override
            public String getConfigurationPath() {
                return tempDir.toString();
            }
        };

        MappingFileUtils.saveMappingToFile(mapping, config);

        Path file = tempDir.resolve("jira-to-polarion.mapping");
        assertTrue(Files.exists(file));

        List<String> lines = Files.readAllLines(file);

        assertEquals("JIRA-1 = WI-001", lines.get(0));
        assertEquals("JIRA-2 = WI-002", lines.get(1));
        assertEquals("JIRA-3 = WI-003", lines.get(2));
    }
}
