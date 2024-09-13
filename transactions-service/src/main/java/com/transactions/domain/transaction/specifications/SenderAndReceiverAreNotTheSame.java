package com.transactions.domain.transaction.specifications;

import com.transactions.domain.transaction.entities.Transaction;
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
