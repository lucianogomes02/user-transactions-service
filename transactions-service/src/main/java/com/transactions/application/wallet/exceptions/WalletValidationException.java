package com.transactions.application.wallet.exceptions;

public class WalletValidationException extends RuntimeException {
    public WalletValidationException(String message) {
        super(message);
    }
}
