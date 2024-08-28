package com.transactions.domain.strategies.transaction;

public class TransactionIsSucceeded implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "Transação já foi concluída";
    }
}
