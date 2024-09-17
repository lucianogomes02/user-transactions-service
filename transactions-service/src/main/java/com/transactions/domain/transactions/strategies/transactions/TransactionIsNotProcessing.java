package com.transactions.domain.transactions.strategies.transactions;

import com.transactions.libs.ValidationMessageStrategy;

public class TransactionIsNotProcessing implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "Transação já foi concluída ou falhou";
    }
}
