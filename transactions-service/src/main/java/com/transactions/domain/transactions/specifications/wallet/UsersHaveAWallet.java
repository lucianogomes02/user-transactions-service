package com.transactions.domain.transactions.specifications.wallet;

import com.transactions.domain.transactions.entities.Wallet;
import org.springframework.stereotype.Component;

@Component
public class UsersHaveAWallet implements WalletSpecification<Wallet> {
    @Override
    public boolean isSatisfiedBy(Wallet wallet) {
        return wallet != null;
    }
}
