package com.transactions.domain.specifications.wallet;

import com.transactions.domain.aggregate.Wallet;

public interface WalletSpecification<T extends Wallet> {
    boolean isSatisfiedBy(T wallet);

    default boolean isSatisfiedBy(T wallet, Double amount) {
        return isSatisfiedBy(wallet) && wallet.getBalance() >= amount;
    }

    default boolean isSatisfiedBy(T receiverWallet, T senderWallet) {
        return isSatisfiedBy(receiverWallet) && isSatisfiedBy(senderWallet);
    }
}
