package ru.yandex.practicum.filmorate.service.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmServiceImpl implements FilmService {

    private final FilmStorage filmStorage;

    @Override
    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    @Override
    public Film findById(Long id) {
        return filmStorage.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%d not found", id)));
    }

    @Override
    public Film create(Film filmRequest) {
        Long id = generateId();

        filmRequest.setId(id);

        filmStorage.save(filmRequest);
        log.info("Film successfully created");

        return filmRequest;
    }

    @Override
    public Film update(Film filmRequest) {
        if (null == filmRequest.getId()) {
            throw new ValidationException("Id should be specified");
        }

        if (filmStorage.findById(filmRequest.getId()).isEmpty()) {
            throw new NotFoundException(String.format("Film with id=%d not found", filmRequest.getId()));
        }

        filmStorage.save(filmRequest);
        log.info("Film successfully updated");

        return filmRequest;
    }

    @Override
    public void addLike(Long id, Long userId) {

    }

    @Override
    public void removeLike(Long id, Long userId) {

    }

    @Override
    public Collection<Film> findTop(Integer count) {
        return null;
    }

    private long generateId() {
        long maxId = filmStorage.findAll()
                .stream()
                .mapToLong(Film::getId)
                .max()
                .orElse(0);

        return ++maxId;
    }

}
