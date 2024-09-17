package com.transactions.domain.transactions.specifications.transactions;

import com.transactions.domain.transactions.entities.Transaction;
import org.springframework.stereotype.Component;

@Component
public class SenderAndReceiverAreNotTheSame implements TransactionSpecification<Transaction> {
    @Override
    public boolean isSatisfiedBy(Transaction transaction) {
        return !transaction
                .senderId
                .equals(transaction.receiverId);
    }
}
