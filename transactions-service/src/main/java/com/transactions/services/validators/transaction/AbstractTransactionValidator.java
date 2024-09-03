package com.transactions.services.validators.transaction;

public interface AbstractTransactionValidator<T>{
    void validate(T t);
}
