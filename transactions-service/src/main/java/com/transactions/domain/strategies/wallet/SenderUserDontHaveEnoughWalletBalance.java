package com.transactions.domain.strategies.wallet;

import com.transactions.domain.strategies.ValidationMessageStrategy;

public class SenderUserDontHaveEnoughWalletBalance implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "Você não possui saldo suficiente para essa transação";
    }
}
