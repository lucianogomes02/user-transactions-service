package com.users.domain.specifications.user;

import com.users.domain.aggregate.User;
import com.users.domain.specifications.Specification;
import org.springframework.stereotype.Component;

@Component
public class CPFIsValid implements Specification<User> {
    @Override
    public boolean isSatisfiedBy(User user) {
        return user.getCpf().matches("^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}\\-[0-9]{2}$");
    }
}
