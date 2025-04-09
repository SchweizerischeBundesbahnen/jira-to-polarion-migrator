package ch.sbb.polarion.test.management.migrator.connector.auth.header;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicAuthHeader extends Header {

    private static final String AUTH_TYPE = "Basic";

    public BasicAuthHeader(String username, String password) {
        super(AUTH_TYPE, createBasicAuth(username, password));
    }

    private static String createBasicAuth(String username, String password) {
        return Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
    }
}
