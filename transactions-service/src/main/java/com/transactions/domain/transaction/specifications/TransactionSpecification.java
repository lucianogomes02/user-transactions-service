package com.transactions.domain.transaction.specifications;

public interface TransactionSpecification<T> {
    boolean isSatisfiedBy(T t);
}
