package com.transactions.domain.transaction.validators;

public interface AbstractTransactionValidator<T>{
    void validate(T t);
}
