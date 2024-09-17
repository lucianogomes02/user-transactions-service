package com.transactions.domain.transactions.strategies.transactions;

import com.transactions.libs.ValidationMessageStrategy;

public class TransactionAmountNotValid implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "O valor da transação deve ser maior que zero";
    }
}
