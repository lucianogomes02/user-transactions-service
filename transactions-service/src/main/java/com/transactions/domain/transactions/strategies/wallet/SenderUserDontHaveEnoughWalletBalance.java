package com.transactions.domain.transactions.strategies.wallet;

import com.transactions.libs.ValidationMessageStrategy;

public class SenderUserDontHaveEnoughWalletBalance implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "Você não possui saldo suficiente para essa transação";
    }
}
