package com.transactions.domain.transactions.specifications.transactions;

public interface TransactionSpecification<T> {
    boolean isSatisfiedBy(T t);
}
