package com.users.services.validators;

import com.users.application.exceptions.UserValidationException;
import com.users.domain.aggregate.User;
import com.users.domain.specifications.Specification;
import com.users.domain.specifications.user.CPFIsUnique;
import com.users.domain.specifications.user.CPFIsValid;
import com.users.domain.specifications.user.EmailIsUnique;
import com.users.domain.specifications.user.EmailIsValid;
import com.users.domain.strategies.*;
import com.users.domain.strategies.user.CPFIsNotUnique;
import com.users.domain.strategies.user.CPFIsNotValid;
import com.users.domain.strategies.user.EmailIsNotUnique;
import com.users.domain.strategies.user.EmailIsNotValid;
import com.users.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class UserValidator implements Validator<User>{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private List<Specification<User>> userSpecifications;

    @PostConstruct
    public void initSpecifications() {
        userSpecifications.add(new CPFIsUnique());
        userSpecifications.add(new CPFIsValid());
        userSpecifications.add(new EmailIsValid());
        userSpecifications.add(new EmailIsUnique());
    }

    @Override
    public void validate(User user) {
        userSpecifications.forEach(
            userSpecification -> {
                var userForValidation = getUserForValidation(user, userSpecification);
                if (!userSpecification.isSatisfiedBy(userForValidation)) {
                    throw new UserValidationException(
                            Objects.requireNonNull(
                                    getValidationMessageStrategy(userSpecification)).getMessage()
                    );
                }
            }
        );
    }

    private User getUserForValidation(User user, Specification<User> userSpecification) {
        return switch (userSpecification.getClass().getSimpleName()) {
            case "CPFIsUnique" -> userRepository.findByCpf(user.getCpf());
            case "EmailIsUnique" -> userRepository.findByEmail(user.getEmail());
            default -> user;
        };
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
