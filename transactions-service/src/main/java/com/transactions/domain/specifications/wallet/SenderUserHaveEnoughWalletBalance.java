package com.transactions.domain.specifications.wallet;

import com.transactions.domain.aggregate.wallet.Wallet;
import org.springframework.stereotype.Component;

@Component
public class SenderUserHaveEnoughWalletBalance implements WalletSpecification<Wallet> {
    @Override
    public boolean isSatisfiedBy(Wallet wallet) {
        return this.isSatisfiedBy(wallet, 0.0);
    }

    @Override
    public boolean isSatisfiedBy(Wallet wallet, Double amount) {
        return wallet.getBalance() >= amount;
    }
}
