package com.users.domain.specifications.user;

import com.users.domain.aggregate.User;
import com.users.domain.specifications.Specification;
import org.springframework.stereotype.Component;

@Component
public class EmailIsUnique implements Specification<User> {
    @Override
    public boolean isSatisfiedBy(User user) {
        return user.getEmail() != null && !user.getEmail().isEmpty();
    }
}
