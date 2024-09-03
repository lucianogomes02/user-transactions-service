package com.transactions.services.validators.wallet;

import com.transactions.domain.aggregate.wallet.Wallet;

public interface AbstractWalletValidator <T extends Wallet>{
    void validate(T wallet1, T wallet2, Double amount);
}
