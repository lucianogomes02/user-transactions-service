package com.transactions.domain.strategies.transaction;

public class TransactionIsProcessing implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "Transação já foi concluída";
    }
}
