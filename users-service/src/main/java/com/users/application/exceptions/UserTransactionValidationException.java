package com.users.application.exceptions;

public class UserTransactionValidationException extends RuntimeException {
    public UserTransactionValidationException(String message) {
        super(message);
    }
}
