package com.users.domain.strategies;

public class EmailIsNotUnique implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "Usuário já cadastrado com o mesmo email";
    }
}
