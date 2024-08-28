package com.transactions.domain.specifications.transaction;

import com.transactions.domain.aggregate.Transaction;
import com.transactions.domain.value_objects.TransactionStatus;

public class TransactionIsNotSucceeded implements Specification<Transaction> {
    @Override
    public boolean isSatisfiedBy(Transaction transaction) {
        return !transaction.getStatus().equals(TransactionStatus.SUCCEEDED);
    }
}
