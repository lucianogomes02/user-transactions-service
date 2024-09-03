package com.transactions.domain.strategies.transaction;

import com.transactions.domain.strategies.ValidationMessageStrategy;

public class TransactionIsNotProcessing implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "Transação já foi concluída ou falhou";
    }
}
