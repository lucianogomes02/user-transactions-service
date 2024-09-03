package com.transactions.domain.strategies.wallet;

import com.transactions.domain.strategies.ValidationMessageStrategy;

public class UsersDontHaveAWallet implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "Usuários não possuem carteira para realizar a transação";
    }
}
