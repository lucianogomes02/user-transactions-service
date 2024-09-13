package com.transactions.domain.transaction.specifications;

import com.transactions.domain.transaction.entities.Transaction;
import com.transactions.domain.transaction.value_objects.TransactionStatus;
import org.springframework.stereotype.Component;

@Component
public class TransactionIsProcessing implements TransactionSpecification<Transaction> {
    @Override
    public boolean isSatisfiedBy(Transaction transaction) {
        return transaction.getStatus().equals(TransactionStatus.PROCESSING);
    }
}
