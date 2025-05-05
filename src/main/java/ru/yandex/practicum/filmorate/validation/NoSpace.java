package ru.yandex.practicum.filmorate.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = NoSpaceValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoSpace {
    String message() default "This field should not contain spaces";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}