package com.transactions.domain.transaction.specifications;

import com.transactions.domain.transaction.aggregate.TransactionAggregate;
import com.transactions.domain.transaction.value_objects.TransactionStatus;
import org.springframework.stereotype.Component;

@Component
public class TransactionIsProcessing implements TransactionSpecification<TransactionAggregate> {
    @Override
    public boolean isSatisfiedBy(TransactionAggregate transaction) {
        return transaction.getStatus().equals(TransactionStatus.PROCESSING);
    }
}
