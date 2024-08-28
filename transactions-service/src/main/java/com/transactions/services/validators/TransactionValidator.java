package com.transactions.services.validators;

import com.transactions.application.exceptions.TransactionValidationException;
import com.transactions.domain.aggregate.Transaction;
import com.transactions.domain.specifications.transaction.SenderAndReceiverAreNotTheSame;
import com.transactions.domain.specifications.transaction.Specification;
import com.transactions.domain.specifications.transaction.TransactionAmountIsValid;
import com.transactions.domain.specifications.transaction.TransactionIsProcessing;
import com.transactions.domain.strategies.transaction.SenderAndReceiverAreTheSame;
import com.transactions.domain.strategies.transaction.TransactionAmountNotValid;
import com.transactions.domain.strategies.transaction.TransactionIsNotProcessing;
import com.transactions.domain.strategies.transaction.ValidationMessageStrategy;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class TransactionValidator implements Validator<Transaction> {
    @Autowired
    protected List<Specification<Transaction>> transactionSpecifications;

    @PostConstruct
    public void initSpecifications() {
        transactionSpecifications.add(new SenderAndReceiverAreNotTheSame());
        transactionSpecifications.add(new TransactionIsProcessing());
        transactionSpecifications.add(new TransactionAmountIsValid());
    }

    @Override
    public void validate(Transaction transaction) {
        transactionSpecifications.forEach(
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

    ValidationMessageStrategy getValidationMessageStrategy(Specification<Transaction> transactionSpecification) {
        return switch (transactionSpecification.getClass().getSimpleName()) {
            case "SenderAndReceiverAreNotTheSame" -> new SenderAndReceiverAreTheSame();
            case "TransactionIsNotProcessing" -> new TransactionIsNotProcessing();
            case "TransactionAmountIsValid" -> new TransactionAmountNotValid();
            default -> null;
        };
    }
}
