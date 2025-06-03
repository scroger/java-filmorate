package ru.yandex.practicum.filmorate.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MinDateValidator implements ConstraintValidator<MinDate, LocalDate> {
    private LocalDate maxDate;

    @Override
    public void initialize(MinDate constraintAnnotation) {
        this.maxDate = LocalDate.parse(constraintAnnotation.value(), DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value == null || !value.isBefore(maxDate);
    }
}