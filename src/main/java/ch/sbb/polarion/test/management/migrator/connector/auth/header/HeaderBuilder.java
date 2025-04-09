package ch.sbb.polarion.test.management.migrator.connector.auth.header;

import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;

public interface HeaderBuilder {
    Header build(MigratorConfig migratorConfig);
}
