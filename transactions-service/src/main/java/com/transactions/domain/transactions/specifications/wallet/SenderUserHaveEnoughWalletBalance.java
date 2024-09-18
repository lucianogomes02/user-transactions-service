package com.transactions.domain.transactions.specifications.wallet;

import com.transactions.domain.transactions.entities.Wallet;
import org.springframework.stereotype.Component;

@Component
public class SenderUserHaveEnoughWalletBalance implements WalletSpecification<Wallet> {
    @Override
    public boolean isSatisfiedBy(Wallet wallet) {
        return this.isSatisfiedBy(wallet, 0.0);
    }

    @Override
    public boolean isSatisfiedBy(Wallet wallet, Double amount) {
        return wallet.balance >= amount;
    }
}
