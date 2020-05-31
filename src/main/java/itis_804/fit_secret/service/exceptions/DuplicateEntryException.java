package itis_804.fit_secret.service.exceptions;

public class DuplicateEntryException extends Exception {
    public DuplicateEntryException() {
        super();
    }

    public DuplicateEntryException(String message) {
        super(message);
    }
}
