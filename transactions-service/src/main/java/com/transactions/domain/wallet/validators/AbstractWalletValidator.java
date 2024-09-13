package com.transactions.domain.wallet.validators;

import com.transactions.domain.wallet.entities.Wallet;

public interface AbstractWalletValidator <T extends Wallet>{
    void validate(T wallet1, T wallet2, Double amount);
}
