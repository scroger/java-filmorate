package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationException extends ResponseStatusException {

    public ValidationException(String message) {
        super(HttpStatus.BAD_REQUEST, message);

        log.error("ValidationException: {}", message);
    }

}
