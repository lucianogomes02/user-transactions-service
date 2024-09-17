package com.transactions.domain.transactions.validators.transactions;

public interface AbstractTransactionValidator<T>{
    void validate(T t);
}
