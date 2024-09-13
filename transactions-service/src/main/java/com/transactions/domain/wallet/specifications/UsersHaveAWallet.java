package com.transactions.domain.wallet.specifications;

import com.transactions.domain.wallet.entities.Wallet;
import org.springframework.stereotype.Component;

@Component
public class UsersHaveAWallet implements WalletSpecification<Wallet> {
    @Override
    public boolean isSatisfiedBy(Wallet wallet) {
        return wallet != null;
    }
}
