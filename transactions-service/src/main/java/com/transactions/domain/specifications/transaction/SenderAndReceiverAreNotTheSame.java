package com.transactions.domain.specifications.transaction;

import com.transactions.domain.aggregate.Transaction;
import org.springframework.stereotype.Component;

@Component
public class SenderAndReceiverAreNotTheSame implements TransactionSpecification<Transaction> {
    @Override
    public boolean isSatisfiedBy(Transaction transaction) {
        return !transaction
                .getSenderId()
                .toString()
                .equals(
                        transaction
                                .getReceiverId()
                                .toString()
                );
    }
}
