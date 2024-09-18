package com.transactions.domain.transactions.specifications.wallet;

import com.transactions.domain.transactions.entities.Wallet;

public interface WalletSpecification<T extends Wallet> {
    boolean isSatisfiedBy(T wallet);

    default boolean isSatisfiedBy(T wallet, Double amount) {
        return isSatisfiedBy(wallet) && wallet.balance >= amount;
    }

    default boolean isSatisfiedBy(T receiverWallet, T senderWallet) {
        return isSatisfiedBy(receiverWallet) && isSatisfiedBy(senderWallet);
    }

    default boolean isSatisfiedBy(T receiverWallet, T senderWallet, Double amount) {
        return isSatisfiedBy(receiverWallet, senderWallet) && senderWallet.balance >= amount;
    }
}
