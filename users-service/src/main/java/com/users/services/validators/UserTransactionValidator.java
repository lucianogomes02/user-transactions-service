package com.users.services.validators;

import com.users.application.exceptions.UserTransactionValidationException;
import com.users.domain.entities.TransactionContext;
import com.users.domain.specifications.Specification;
import com.users.domain.specifications.user_transaction.UserSenderHasEnoughWalletFunds;
import com.users.domain.specifications.user_transaction.UsersSenderAndReceiverExists;
import com.users.domain.strategies.ValidationMessageStrategy;
import com.users.domain.strategies.user_transaction.UserSenderHasNotEnoughWalletFunds;
import com.users.domain.strategies.user_transaction.UsersSenderOrReceiverDoNotExist;
import com.users.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class UserTransactionValidator implements Validator<TransactionContext> {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private List<Specification<TransactionContext>> userTransactionSpecifications;

    @PostConstruct
    public void initSpecifications() {
        userTransactionSpecifications.add(new UserSenderHasEnoughWalletFunds());
        userTransactionSpecifications.add(new UsersSenderAndReceiverExists());
    }

    @Override
    public void validate(TransactionContext transactionContext) {
        userTransactionSpecifications.forEach(
            userTransactionSpecification -> {
                if (!userTransactionSpecification.isSatisfiedBy(transactionContext)) {
                    throw new UserTransactionValidationException(
                        Objects.requireNonNull(
                            getValidationMessageStrategy(userTransactionSpecification)).getMessage()
                    );
                }
            }
        );
    }

    private ValidationMessageStrategy getValidationMessageStrategy(Specification<TransactionContext> userTransactionSpecification) {
        return switch (userTransactionSpecification.getClass().getSimpleName()) {
            case "UserSenderHasEnoughWalletFunds" -> new UserSenderHasNotEnoughWalletFunds();
            case "UsersSenderAndReceiverExists" -> new UsersSenderOrReceiverDoNotExist();
            default -> null;
        };
    }
}
