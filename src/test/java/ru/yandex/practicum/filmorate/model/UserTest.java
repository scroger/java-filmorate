package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void testValidateEmail() {
        User user = new User();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());

        //set required fields
        user.setLogin("login");

        //set empty email
        user.setEmail("");
        violations = validator.validate(user);
        assertFalse(violations.isEmpty());

        //set blank email
        user.setEmail(" ");
        violations = validator.validate(user);
        assertFalse(violations.isEmpty());

        //set invalid email
        user.setEmail("email");
        violations = validator.validate(user);
        assertFalse(violations.isEmpty());

        //set email
        user.setEmail("valid@mail.ru");
        violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testValidateLogin() {
        User user = new User();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());

        //set required fields
        user.setEmail("valid@mail.ru");

        //set empty login
        user.setLogin("");
        violations = validator.validate(user);
        assertFalse(violations.isEmpty());

        //set blank login
        user.setLogin(" ");
        violations = validator.validate(user);
        assertFalse(violations.isEmpty());

        //set login
        user.setLogin("login");
        violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testValidateBirthday() {
        User user = new User();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());

        //set required fields
        user.setLogin("login");
        user.setEmail("valid@mail.ru");

        //set future birthday
        user.setBirthday(LocalDate.now().plusDays(1));
        violations = validator.validate(user);
        assertFalse(violations.isEmpty());

        //set birthday
        user.setBirthday(LocalDate.now().minusDays(1));
        violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

}