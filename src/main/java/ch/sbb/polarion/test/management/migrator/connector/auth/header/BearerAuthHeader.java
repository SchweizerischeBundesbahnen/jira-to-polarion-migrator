package ch.sbb.polarion.test.management.migrator.connector.auth.header;

public class BearerAuthHeader extends Header {

    private static final String AUTH_TYPE = "Bearer";

    public BearerAuthHeader(String token) {
        super(AUTH_TYPE, token);
    }
}
