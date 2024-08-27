package com.users.domain.specifications.user_transaction;

import com.users.domain.aggregate.User;
import com.users.domain.specifications.Specification;

import java.util.List;
import java.util.Objects;

public class UsersSenderAndReceiverExists implements Specification<List<User>> {
    @Override
    public boolean isSatisfiedBy(List<User> users) {
        return users != null && users.stream().anyMatch(Objects::nonNull);
    }
}
