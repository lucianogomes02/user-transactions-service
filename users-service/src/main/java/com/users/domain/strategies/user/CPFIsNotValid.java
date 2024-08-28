package com.users.domain.strategies.user;

import com.users.domain.strategies.ValidationMessageStrategy;

public class CPFIsNotValid implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "CPF inválido";
    }
}
