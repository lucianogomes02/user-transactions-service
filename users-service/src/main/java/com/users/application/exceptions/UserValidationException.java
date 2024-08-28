package com.users.application.exceptions;

import jakarta.validation.ValidationException;

public class UserValidationException extends ValidationException {
    public UserValidationException(String message) {
        super(message);
    }
}
