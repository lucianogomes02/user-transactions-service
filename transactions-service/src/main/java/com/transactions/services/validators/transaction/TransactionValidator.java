package com.transactions.services.validators.transaction;

import com.transactions.application.exceptions.TransactionValidationException;
import com.transactions.domain.aggregate.transaction.Transaction;
import com.transactions.domain.specifications.transaction.SenderAndReceiverAreNotTheSame;
import com.transactions.domain.specifications.transaction.TransactionSpecification;
import com.transactions.domain.specifications.transaction.TransactionAmountIsValid;
import com.transactions.domain.specifications.transaction.TransactionIsProcessing;
import com.transactions.domain.strategies.transaction.SenderAndReceiverAreTheSame;
import com.transactions.domain.strategies.transaction.TransactionAmountNotValid;
import com.transactions.domain.strategies.transaction.TransactionIsNotProcessing;
import com.transactions.domain.strategies.ValidationMessageStrategy;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class TransactionValidator implements AbstractTransactionValidator<Transaction> {
    @Autowired
    protected List<TransactionSpecification<Transaction>> transactionTransactionSpecifications;

    @PostConstruct
    public void initSpecifications() {
        transactionTransactionSpecifications.add(new SenderAndReceiverAreNotTheSame());
        transactionTransactionSpecifications.add(new TransactionIsProcessing());
        transactionTransactionSpecifications.add(new TransactionAmountIsValid());
    }

    @Override
    public void validate(Transaction transaction) {
        transactionTransactionSpecifications.forEach(
            transactionSpecification -> {
                if (!transactionSpecification.isSatisfiedBy(transaction)) {
                    throw new TransactionValidationException(
                            Objects.requireNonNull(
                                    getValidationMessageStrategy(transactionSpecification)).getMessage()
                    );
                }
            }
        );
    }

    ValidationMessageStrategy getValidationMessageStrategy(TransactionSpecification<Transaction> transactionSpecification) {
        return switch (transactionSpecification.getClass().getSimpleName()) {
            case "SenderAndReceiverAreNotTheSame" -> new SenderAndReceiverAreTheSame();
            case "TransactionIsNotProcessing" -> new TransactionIsNotProcessing();
            case "TransactionAmountIsValid" -> new TransactionAmountNotValid();
            default -> null;
        };
    }
}
