package com.transactions.domain.transactions.validators.transactions;

import com.transactions.application.transaction.exceptions.TransactionValidationException;
import com.transactions.domain.transactions.entities.Transaction;
import com.transactions.domain.transactions.specifications.transactions.SenderAndReceiverAreNotTheSame;
import com.transactions.domain.transactions.specifications.transactions.TransactionAmountIsValid;
import com.transactions.domain.transactions.specifications.transactions.TransactionIsProcessing;
import com.transactions.domain.transactions.specifications.transactions.TransactionSpecification;
import com.transactions.domain.transactions.strategies.transactions.SenderAndReceiverAreTheSame;
import com.transactions.domain.transactions.strategies.transactions.TransactionAmountNotValid;
import com.transactions.domain.transactions.strategies.transactions.TransactionIsNotProcessing;
import com.transactions.libs.ValidationMessageStrategy;
import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TransactionValidator implements AbstractTransactionValidator<Transaction> {

    private final List<TransactionSpecification<Transaction>> transactionTransactionSpecifications = new ArrayList<>();

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
