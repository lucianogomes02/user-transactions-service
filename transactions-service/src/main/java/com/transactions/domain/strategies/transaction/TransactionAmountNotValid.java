package com.transactions.domain.strategies.transaction;

import com.transactions.domain.strategies.ValidationMessageStrategy;

public class TransactionAmountNotValid implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "O valor da transação deve ser maior que zero";
    }
}
