package com.transactions.domain.specifications.transaction;

public interface Specification <T> {
    boolean isSatisfiedBy(T t);
}
