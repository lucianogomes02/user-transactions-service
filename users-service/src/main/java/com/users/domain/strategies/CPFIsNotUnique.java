package com.users.domain.strategies;

public class CPFIsNotUnique implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "Usuário já cadastrado com o mesmo CPF";
    }
}
