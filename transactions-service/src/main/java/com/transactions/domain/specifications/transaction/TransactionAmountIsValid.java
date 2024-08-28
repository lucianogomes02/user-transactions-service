package com.transactions.domain.specifications.transaction;

import com.transactions.domain.aggregate.Transaction;

public class TransactionAmountIsValid implements Specification<Transaction> {
    @Override
    public boolean isSatisfiedBy(Transaction transaction) {
        return transaction.getAmount() > 0;
    }
}
