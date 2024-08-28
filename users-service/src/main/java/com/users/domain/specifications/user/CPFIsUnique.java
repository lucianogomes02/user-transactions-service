package com.users.domain.specifications.user;

import com.users.domain.aggregate.User;
import com.users.domain.specifications.Specification;
import org.springframework.stereotype.Component;

@Component
public class CPFIsUnique implements Specification<User> {
    @Override
    public boolean isSatisfiedBy(User user) {
        return user == null;
    }
}
