package com.transactions.application.exceptions;

public class WalletValidationException extends RuntimeException {
    public WalletValidationException(String message) {
        super(message);
    }
}
