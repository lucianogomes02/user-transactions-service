package com.transactions.domain.transactions.strategies.wallet;

import com.transactions.libs.ValidationMessageStrategy;

public class UsersDontHaveAWallet implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "Usuários não possuem carteira para realizar a transação";
    }
}
