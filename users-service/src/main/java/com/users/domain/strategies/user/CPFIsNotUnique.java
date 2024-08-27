package com.users.domain.strategies.user;

import com.users.domain.strategies.ValidationMessageStrategy;

public class CPFIsNotUnique implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "Usuário já cadastrado com o mesmo CPF";
    }
}
