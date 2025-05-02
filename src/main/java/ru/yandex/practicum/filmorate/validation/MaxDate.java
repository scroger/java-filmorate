package ru.yandex.practicum.filmorate.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxDateValidator.class)
public @interface MaxDate {
    String message() default "Release date should be before maxDate";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String value();
}