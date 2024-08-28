package com.users.domain.strategies.user_transaction;

import com.users.domain.strategies.ValidationMessageStrategy;

public class UserSenderHasNotEnoughWalletFunds implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "Usuário remetente não possui saldo suficiente na carteira";
    }
}
