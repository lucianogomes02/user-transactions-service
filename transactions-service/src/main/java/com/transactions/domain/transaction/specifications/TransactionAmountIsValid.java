package com.transactions.domain.transaction.specifications;

import com.transactions.domain.transaction.entities.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionAmountIsValid implements TransactionSpecification<Transaction> {
    @Override
    public boolean isSatisfiedBy(Transaction transaction) {
        return transaction.getAmount() > 0;
    }
}
