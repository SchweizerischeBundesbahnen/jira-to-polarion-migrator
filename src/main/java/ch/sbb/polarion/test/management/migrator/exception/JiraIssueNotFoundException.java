package ch.sbb.polarion.test.management.migrator.exception;

public class JiraIssueNotFoundException extends RuntimeException {

    public JiraIssueNotFoundException(String message) {
        super(message);
    }
}
