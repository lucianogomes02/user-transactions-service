package com.users.services.validators;

import com.users.application.exceptions.UserValidationException;
import com.users.domain.aggregate.User;
import com.users.domain.specifications.Specification;
import com.users.domain.strategies.*;
import com.users.domain.strategies.user.CPFIsNotUnique;
import com.users.domain.strategies.user.CPFIsNotValid;
import com.users.domain.strategies.user.EmailIsNotUnique;
import com.users.domain.strategies.user.EmailIsNotValid;
import com.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class UserValidator implements Validator<User>{
    private final List<Specification<User>> userSpecifications;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserValidator(List<Specification<User>> userSpecifications) {
        this.userSpecifications = userSpecifications;
    }

    @Override
    public void validate(User user) {
        userSpecifications.forEach(
            userSpecification -> {
                if (!userSpecification.isSatisfiedBy(user)) {
                    throw new UserValidationException(
                            Objects.requireNonNull(
                                    getValidationMessageStrategy(userSpecification)).getMessage()
                    );
                }
            }
        );
    }

    private ValidationMessageStrategy getValidationMessageStrategy(Specification<User> userSpecification) {
        return switch (userSpecification.getClass().getSimpleName()) {
            case "CPFIsUnique" -> new CPFIsNotUnique();
            case "CPFIsValid" -> new CPFIsNotValid();
            case "EmailIsValid" -> new EmailIsNotValid();
            case "EmailIsUnique" -> new EmailIsNotUnique();
            default -> null;
        };
    }
}
