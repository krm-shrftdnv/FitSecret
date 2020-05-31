package com.company.fit_secret.service.exceptions;

public class DuplicateEntryException extends Exception {
    public DuplicateEntryException() {
        super();
    }

    public DuplicateEntryException(String message) {
        super(message);
    }
}
