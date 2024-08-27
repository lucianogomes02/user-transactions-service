package com.users.domain.strategies;

public class EmailIsNotValid implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "Email inválido";
    }
}
