package com.users.domain.strategies.user;

import com.users.domain.strategies.ValidationMessageStrategy;

public class EmailIsNotValid implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "Email inválido";
    }
}
