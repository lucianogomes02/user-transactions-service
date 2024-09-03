package com.transactions.domain.specifications.transaction;

public interface TransactionSpecification<T> {
    boolean isSatisfiedBy(T t);
}
