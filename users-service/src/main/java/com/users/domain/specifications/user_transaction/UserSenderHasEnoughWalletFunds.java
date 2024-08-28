package com.users.domain.specifications.user_transaction;

import com.users.domain.entities.TransactionContext;
import com.users.domain.specifications.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserSenderHasEnoughWalletFunds implements Specification<TransactionContext> {
    @Override
    public boolean isSatisfiedBy(TransactionContext transactionContext) {
        return transactionContext
                .getUserSender()
                .getWalletFunds() >= transactionContext.getAmount();
    }
}
