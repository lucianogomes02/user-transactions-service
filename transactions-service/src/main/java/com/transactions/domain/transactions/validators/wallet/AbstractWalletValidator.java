package com.transactions.domain.transactions.validators.wallet;

import com.transactions.domain.transactions.entities.Wallet;

public interface AbstractWalletValidator <T extends Wallet>{
    void validate(T wallet1, T wallet2, Double amount);
}
