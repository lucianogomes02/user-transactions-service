package com.transactions.domain.transactions.specifications.transactions;

import com.transactions.domain.transactions.entities.Transaction;
import com.transactions.domain.transactions.value_objects.transactions.TransactionStatus;
import org.springframework.stereotype.Component;

@Component
public class TransactionIsProcessing implements TransactionSpecification<Transaction> {
    @Override
    public boolean isSatisfiedBy(Transaction transaction) {
        return transaction.status.equals(TransactionStatus.PROCESSING);
    }
}
