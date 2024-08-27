package com.users.services.validators;

import com.users.application.exceptions.UserValidationException;
import com.users.domain.aggregate.User;
import com.users.domain.specifications.Specification;
import com.users.domain.specifications.user.CPFIsUnique;
import com.users.domain.specifications.user.CPFIsValid;
import com.users.domain.specifications.user.EmailIsUnique;
import com.users.domain.specifications.user.EmailIsValid;
import com.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
                    throw new UserValidationException(getValidationExceptionMessage(userSpecification));
                }
            }
        );
    }

    private String getValidationExceptionMessage(Specification<User> userSpecification) {
        if (userSpecification instanceof CPFIsUnique) {
            return "Usuário já cadastrado com o mesmo CPF";
        } else if (userSpecification instanceof CPFIsValid) {
            return "CPF inválido";
        } else if (userSpecification instanceof EmailIsValid) {
            return "Email inválido";
        } else if (userSpecification instanceof EmailIsUnique) {
            return "Usuário já cadastrado com o mesmo email";
        } else {
            return "Validações falaharam";
        }
    }
}
