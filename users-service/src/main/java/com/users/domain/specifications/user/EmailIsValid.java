package com.users.domain.specifications.user;

import com.users.domain.aggregate.User;
import com.users.domain.specifications.Specification;
import org.springframework.stereotype.Component;

@Component
public class EmailIsValid implements Specification<User> {
    @Override
    public boolean isSatisfiedBy(User user) {
        return user.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }
}
