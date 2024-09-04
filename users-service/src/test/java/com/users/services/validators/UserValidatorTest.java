//package com.users.services.validators;
//
//import com.users.application.exceptions.UserValidationException;
//import com.users.domain.aggregate.User;
//import com.users.domain.specifications.Specification;
//import com.users.domain.strategies.user.CPFIsNotUnique;
//import com.users.domain.strategies.user.CPFIsNotValid;
//import com.users.domain.strategies.user.EmailIsNotUnique;
//import com.users.domain.strategies.user.EmailIsNotValid;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//
//@PrepareForTest(UserValidator.class)
//public class UserValidatorTest {
//    @Mock
//    private Specification<User> CPFIsUnique;
//
//    @Mock
//    private Specification<User> EmailIsUnique;
//
//    @Mock
//    private Specification<User> CPFIsValid;
//
//    @Mock
//    private Specification<User> EmailIsValid;
//
//    @InjectMocks
//    private UserValidator userValidator;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        List<Specification<User>> specifications = new ArrayList<>();
//        specifications.add(CPFIsUnique);
//        specifications.add(EmailIsUnique);
//        specifications.add(CPFIsValid);
//        specifications.add(EmailIsValid);
//        userValidator.userSpecifications = specifications;
//
//        when(CPFIsUnique.isSatisfiedBy(any(User.class))).thenReturn(true);
//        when(EmailIsUnique.isSatisfiedBy(any(User.class))).thenReturn(true);
//        when(CPFIsValid.isSatisfiedBy(any(User.class))).thenReturn(true);
//        when(EmailIsValid.isSatisfiedBy(any(User.class))).thenReturn(true);
//    }
//
//    @Test
//    public void testValidateSuccess() {
//        User user = new User(
//            "John Doe",
//            "john.doe@teste.com",
//            "12345",
//            "123.456.789-00"
//        );
//
//        assertDoesNotThrow(() -> userValidator.validate(user));
//    }
//
//    @Test
//    public void testValidateCPFIsNotUnique() throws Exception {
//        User user = new User(
//            "John Doe",
//            "john.doe2@teste.com",
//            "12345",
//            "123.456.789-00"
//        );
//
//
//        UserValidator spyValidator = spy(new UserValidator());
//        List<Specification<User>> specifications = new ArrayList<>();
//        specifications.add(userValidator.userSpecifications.get(0));
//        specifications.add(userValidator.userSpecifications.get(1));
//        specifications.add(userValidator.userSpecifications.get(2));
//        specifications.add(userValidator.userSpecifications.get(3));
//        spyValidator.userSpecifications = specifications;
//        when(spyValidator.userSpecifications.get(0).isSatisfiedBy(user)).thenReturn(false);
//        when(spyValidator.getValidationMessageStrategy(CPFIsUnique)).thenReturn(new CPFIsNotUnique());
//
//        UserValidationException thrown = assertThrows(
//                UserValidationException.class,
//                () -> spyValidator.validate(user)
//        );
//        assertEquals("Usuário já cadastrado com o mesmo CPF", thrown.getMessage());
//    }
//
//    @Test
//    public void testValidateEmailIsNotUnique() {
//        User user = new User(
//                "John Doe",
//                "john.doe@teste.com",
//                "12345",
//                "123.456.719-00"
//        );
//
//        UserValidator spyValidator = spy(new UserValidator());
//        List<Specification<User>> specifications = new ArrayList<>();
//        specifications.add(userValidator.userSpecifications.get(0));
//        specifications.add(userValidator.userSpecifications.get(1));
//        specifications.add(userValidator.userSpecifications.get(2));
//        specifications.add(userValidator.userSpecifications.get(3));
//        spyValidator.userSpecifications = specifications;
//        when(spyValidator.userSpecifications.get(1).isSatisfiedBy(user)).thenReturn(false);
//        when(spyValidator.getValidationMessageStrategy(EmailIsUnique)).thenReturn(new EmailIsNotUnique());
//
//        UserValidationException thrown = assertThrows(
//                UserValidationException.class,
//                () -> spyValidator.validate(user)
//        );
//        assertEquals("Usuário já cadastrado com o mesmo email", thrown.getMessage());
//    }
//
//    @Test
//    public void testValidateCPFIsNotValid() {
//        User user = new User(
//                "John Doe",
//                "john.doe@teste.com",
//                "12345",
//                "0"
//        );
//
//        UserValidator spyValidator = spy(new UserValidator());
//        List<Specification<User>> specifications = new ArrayList<>();
//        specifications.add(userValidator.userSpecifications.get(0));
//        specifications.add(userValidator.userSpecifications.get(1));
//        specifications.add(userValidator.userSpecifications.get(2));
//        specifications.add(userValidator.userSpecifications.get(3));
//        spyValidator.userSpecifications = specifications;
//        when(spyValidator.userSpecifications.get(2).isSatisfiedBy(user)).thenReturn(false);
//        when(spyValidator.getValidationMessageStrategy(CPFIsValid)).thenReturn(new CPFIsNotValid());
//
//        UserValidationException thrown = assertThrows(
//                UserValidationException.class,
//                () -> spyValidator.validate(user)
//        );
//        assertEquals("CPF inválido", thrown.getMessage());
//    }
//
//    @Test
//    public void testValidateEmailIsNotValid() {
//        User user = new User(
//            "John Doe",
//            "john.doe@",
//            "12345",
//            "123.556.719-00"
//        );
//
//        UserValidator spyValidator = spy(new UserValidator());
//        List<Specification<User>> specifications = new ArrayList<>();
//        specifications.add(userValidator.userSpecifications.get(0));
//        specifications.add(userValidator.userSpecifications.get(1));
//        specifications.add(userValidator.userSpecifications.get(2));
//        specifications.add(userValidator.userSpecifications.get(3));
//        spyValidator.userSpecifications = specifications;
//        when(spyValidator.userSpecifications.get(3).isSatisfiedBy(user)).thenReturn(false);
//        when(spyValidator.getValidationMessageStrategy(EmailIsValid)).thenReturn(new EmailIsNotValid());
//
//        UserValidationException thrown = assertThrows(
//                UserValidationException.class,
//                () -> spyValidator.validate(user)
//        );
//        assertEquals("Email inválido", thrown.getMessage());
//    }
//}