package ch.sbb.polarion.test.management.migrator.connector.auth.header;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class Header {
    private String authType;

    private String token;

    @Override
    public String toString() {
        return String.format("%s %s", authType, token);
    }
}
