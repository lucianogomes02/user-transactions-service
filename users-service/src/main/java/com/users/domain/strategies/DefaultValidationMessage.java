package com.users.domain.strategies;

public class DefaultValidationMessage implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "Erro na validação do usuário";
    }
}
