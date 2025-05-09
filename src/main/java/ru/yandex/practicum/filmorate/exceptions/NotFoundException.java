package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotFoundException extends ResponseStatusException {

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);

        log.error("NotFoundException: {}", message);
    }

}
