package ch.sbb.polarion.test.management.migrator.utils;

import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@UtilityClass
public class MappingFileUtils {

    public static final String JIRA_TO_POLARION_MAPPING = "jira-to-polarion.mapping";

    public static String getMappingFile(MigratorConfig config) {
        return config.getConfigurationPath() + File.separator + JIRA_TO_POLARION_MAPPING;
    }

    public static void deleteMappingFile(MigratorConfig config) throws IOException {
        String mappingFile = getMappingFile(config);
        Path file = Paths.get(mappingFile);
        if (Files.deleteIfExists(file)) {
            log.info("{} has been deleted", mappingFile);
        }
    }

    public static void saveMappingToFile(Map<String, String> mapping, MigratorConfig config) throws IOException {
        Map<String, String> sortedMap = new TreeMap<>(mapping);
        String mappingFile = getMappingFile(config);
        Path file = Paths.get(mappingFile);

        Files.write(file, () -> sortedMap.entrySet().stream()
                .<CharSequence>map(entry -> entry.getKey() + " = " + entry.getValue())
                .iterator());

        log.info("Mapping saved to {}", mappingFile);
    }
}
