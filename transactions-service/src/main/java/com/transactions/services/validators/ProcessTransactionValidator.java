package com.transactions.services.validators;

import com.transactions.application.exceptions.TransactionValidationException;
import com.transactions.domain.aggregate.Transaction;
import com.transactions.domain.specifications.transaction.Specification;
import com.transactions.domain.specifications.transaction.TransactionIsProcessing;
import com.transactions.domain.strategies.transaction.TransactionIsNotProcessing;
import com.transactions.domain.strategies.transaction.ValidationMessageStrategy;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ProcessTransactionValidator implements Validator<Transaction> {
    @Autowired
    private List<Specification<Transaction>> processTransactionSpecifications;

    @PostConstruct
    public void initSpecifications() {
        processTransactionSpecifications.add(new TransactionIsProcessing());
    }

    @Override
    public void validate(Transaction transaction) {
        processTransactionSpecifications.forEach(
            transactionSpecification -> {
                if (!transactionSpecification.isSatisfiedBy(transaction)) {
                    throw new TransactionValidationException(
                            Objects.requireNonNull(getValidationMessageStrategy(transactionSpecification)).getMessage()
                    );
                }
            }
        );
    }

    private ValidationMessageStrategy getValidationMessageStrategy(Specification<Transaction> transactionSpecification) {
        return switch (transactionSpecification.getClass().getSimpleName()) {
            case "TransactionIsProcessing" -> new TransactionIsNotProcessing();
            default -> null;
        };
    }
}
