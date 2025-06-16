package ru.yandex.practicum.filmorate.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinDateValidator.class)
public @interface MinDate {
    String message() default "Release date should be after minDate";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String value();
}