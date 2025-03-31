package ch.sbb.polarion.test.management.migrator.connector.auth.header;

import ch.sbb.polarion.test.management.migrator.config.MigratorConfig;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PolarionHeaderBuilder implements HeaderBuilder {

    @Override
    public Header build() {
        return new BearerAuthHeader(MigratorConfig.getInstance().getPolarionSecurityAccessToken());
    }
}
