package ch.sbb.polarion.test.management.migrator.exception;

public class InvalidMigratorConfigurationException extends RuntimeException {
    public InvalidMigratorConfigurationException(String message) {
        super(message);
    }

    public InvalidMigratorConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
