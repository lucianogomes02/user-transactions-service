package com.users.domain.specifications;

public interface Specification <T> {
    boolean isSatisfiedBy(T t);
}
