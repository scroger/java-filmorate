package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Long, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> list() {
        return films.values();
    }

    @PostMapping
    public Film create(@RequestBody @Valid Film filmRequest) {
        Long id = generateId();

        filmRequest.setId(id);

        films.put(id, filmRequest);
        log.info("Film successfully created");

        return filmRequest;
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film filmRequest) {
        if (null == filmRequest.getId()) {
            throw new ValidationException("Id should be specified");
        }
        if (!films.containsKey(filmRequest.getId())) {
            throw new ValidationException(String.format("Film with id=%d not found", filmRequest.getId()));
        }

        films.put(filmRequest.getId(), filmRequest);
        log.info("Film successfully updated");

        return filmRequest;
    }

    private long generateId() {
        long maxId = films.keySet().stream().mapToLong(id -> id).max().orElse(0);

        return ++maxId;
    }

}
