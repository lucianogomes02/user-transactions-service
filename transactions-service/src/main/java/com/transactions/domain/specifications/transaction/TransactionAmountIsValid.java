package com.transactions.domain.specifications.transaction;

import com.transactions.domain.aggregate.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionAmountIsValid implements TransactionSpecification<Transaction> {
    @Override
    public boolean isSatisfiedBy(Transaction transaction) {
        return transaction.getAmount() > 0;
    }
}
