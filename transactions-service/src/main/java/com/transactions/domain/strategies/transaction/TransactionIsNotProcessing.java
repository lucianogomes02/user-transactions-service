package com.transactions.domain.strategies.transaction;

public class TransactionIsNotProcessing implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "Transação já foi concluída ou falhou";
    }
}
