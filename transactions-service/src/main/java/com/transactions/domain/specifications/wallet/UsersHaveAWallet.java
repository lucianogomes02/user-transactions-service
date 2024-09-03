package com.transactions.domain.specifications.wallet;

import com.transactions.domain.aggregate.Wallet;

public class UsersHaveAWallet implements WalletSpecification<Wallet> {
    @Override
    public boolean isSatisfiedBy(Wallet wallet) {
        return wallet != null;
    }
}
