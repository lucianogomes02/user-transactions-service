package com.users.domain.strategies.user_transaction;

import com.users.domain.strategies.ValidationMessageStrategy;

public class UsersSenderOrReceiverDoNotExist implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "Usuário remetente ou destinatário não existem";
    }
}
