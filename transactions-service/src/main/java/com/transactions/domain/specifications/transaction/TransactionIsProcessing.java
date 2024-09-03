package com.transactions.domain.specifications.transaction;

import com.transactions.domain.aggregate.transaction.Transaction;
import com.transactions.domain.value_objects.TransactionStatus;
import org.springframework.stereotype.Component;

@Component
public class TransactionIsProcessing implements TransactionSpecification<Transaction> {
    @Override
    public boolean isSatisfiedBy(Transaction transaction) {
        return transaction.getStatus().equals(TransactionStatus.PROCESSING);
    }
}
