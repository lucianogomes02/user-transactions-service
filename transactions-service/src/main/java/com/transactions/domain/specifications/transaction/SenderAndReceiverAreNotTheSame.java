package com.transactions.domain.specifications.transaction;

import com.transactions.domain.aggregate.Transaction;
import org.springframework.transaction.reactive.TransactionContext;

public class SenderAndReceiverAreNotTheSame implements Specification<Transaction> {
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
