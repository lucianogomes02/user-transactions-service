package com.transactions.domain.strategies.transaction;

public class TransactionAmountNotValid implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "O valor da transação deve ser maior que zero";
    }
}
