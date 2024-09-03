package com.transactions.application.exceptions.wallet;

public class WalletValidationException extends RuntimeException {
    public WalletValidationException(String message) {
        super(message);
    }
}
