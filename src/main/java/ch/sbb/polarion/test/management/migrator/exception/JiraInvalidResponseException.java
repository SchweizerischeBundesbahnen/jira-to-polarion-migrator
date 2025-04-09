package ch.sbb.polarion.test.management.migrator.exception;

public class JiraInvalidResponseException extends RuntimeException {

    public JiraInvalidResponseException(String message) {
        super(message);
    }
}
