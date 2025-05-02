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

class FilmTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void testValidator() {
        Film film = new Film();
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());

        //set id
        film.setId(1L);
        violations = validator.validate(film);
        assertFalse(violations.isEmpty());

        //set empty name
        film.setName("");
        violations = validator.validate(film);
        assertFalse(violations.isEmpty());

        //set blank name
        film.setName(" ");
        violations = validator.validate(film);
        assertFalse(violations.isEmpty());

        //set name
        film.setName("film name");
        violations = validator.validate(film);
        assertTrue(violations.isEmpty());

        //set max description + 1
        film.setDescription("test".repeat(200 / 4) + "!");
        violations = validator.validate(film);
        assertFalse(violations.isEmpty());

        //set max description
        film.setDescription("test".repeat(200 / 4));
        violations = validator.validate(film);
        assertTrue(violations.isEmpty());

        //set release date
        film.setReleaseDate(LocalDate.now());
        violations = validator.validate(film);
        assertFalse(violations.isEmpty());

        //set release date before 1895-02-28
        film.setReleaseDate(LocalDate.of(1894, 2, 27));
        violations = validator.validate(film);
        assertTrue(violations.isEmpty());

        //set negative duration
        film.setDuration(-100L);
        violations = validator.validate(film);
        assertFalse(violations.isEmpty());

        //set duration
        film.setDuration(100L);
        violations = validator.validate(film);
        assertTrue(violations.isEmpty());
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

}