package com.users.domain.strategies;

public class CPFIsNotValid implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "CPF inválido";
    }
}
