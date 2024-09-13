package com.transactions.domain.transaction.specifications;

import com.transactions.domain.transaction.aggregate.TransactionAggregate;
import org.springframework.stereotype.Component;

@Component
public class TransactionAmountIsValid implements TransactionSpecification<TransactionAggregate> {
    @Override
    public boolean isSatisfiedBy(TransactionAggregate transaction) {
        return transaction.getAmount() > 0;
    }
}
