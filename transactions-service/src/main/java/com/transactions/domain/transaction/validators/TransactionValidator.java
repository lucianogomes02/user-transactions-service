package com.transactions.domain.transaction.validators;

import com.transactions.application.transaction.exceptions.TransactionValidationException;
import com.transactions.domain.transaction.aggregate.TransactionAggregate;
import com.transactions.domain.transaction.specifications.SenderAndReceiverAreNotTheSame;
import com.transactions.domain.transaction.specifications.TransactionAmountIsValid;
import com.transactions.domain.transaction.specifications.TransactionIsProcessing;
import com.transactions.domain.transaction.specifications.TransactionSpecification;
import com.transactions.domain.transaction.strategies.SenderAndReceiverAreTheSame;
import com.transactions.domain.transaction.strategies.TransactionAmountNotValid;
import com.transactions.domain.transaction.strategies.TransactionIsNotProcessing;
import com.transactions.libs.ValidationMessageStrategy;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class TransactionValidator implements AbstractTransactionValidator<TransactionAggregate> {
    @Autowired
    protected List<TransactionSpecification<TransactionAggregate>> transactionTransactionSpecifications;

    @PostConstruct
    public void initSpecifications() {
        transactionTransactionSpecifications.add(new SenderAndReceiverAreNotTheSame());
        transactionTransactionSpecifications.add(new TransactionIsProcessing());
        transactionTransactionSpecifications.add(new TransactionAmountIsValid());
    }

    @Override
    public void validate(TransactionAggregate transaction) {
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

    ValidationMessageStrategy getValidationMessageStrategy(TransactionSpecification<TransactionAggregate> transactionSpecification) {
        return switch (transactionSpecification.getClass().getSimpleName()) {
            case "SenderAndReceiverAreNotTheSame" -> new SenderAndReceiverAreTheSame();
            case "TransactionIsNotProcessing" -> new TransactionIsNotProcessing();
            case "TransactionAmountIsValid" -> new TransactionAmountNotValid();
            default -> null;
        };
    }
}
