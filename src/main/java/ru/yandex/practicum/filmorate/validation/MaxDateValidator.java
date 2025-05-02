package ru.yandex.practicum.filmorate.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MaxDateValidator implements ConstraintValidator<MaxDate, LocalDate> {
    private LocalDate maxDate;

    @Override
    public void initialize(MaxDate constraintAnnotation) {
        this.maxDate = LocalDate.parse(constraintAnnotation.value(), DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value == null || value.isBefore(maxDate);
    }
}