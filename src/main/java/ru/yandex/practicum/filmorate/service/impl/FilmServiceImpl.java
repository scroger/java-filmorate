package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmServiceImpl implements FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;

    @Override
    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    @Override
    public Film findById(Long id) {
        return filmStorage.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Film with id=%d not found.", id)));
    }

    @Override
    public Film create(Film filmRequest) {
        Long id = generateId();

        filmRequest.setId(id);

        filmStorage.create(filmRequest);
        log.info("Film successfully created");

        return filmRequest;
    }

    @Override
    public Film update(Film filmRequest) {
        if (null == filmRequest.getId()) {
            throw new ValidationException("Id should be specified");
        }

        Long filmId = filmRequest.getId();
        Film film = filmStorage.findById(filmId)
                .orElseThrow(() -> new NotFoundException(String.format("Film with id=%d not found.", filmId)));
        filmRequest.setLikes(film.getLikes());

        filmStorage.update(filmRequest);
        log.info("Film successfully updated");

        return filmRequest;
    }

    @Override
    public void addLike(Long id, Long userId) {
        filmStorage.addLike(findById(id), userService.findById(userId));

        log.info("User with id={} liked film with id={}", userId, id);
    }

    @Override
    public void removeLike(Long id, Long userId) {
        if (filmStorage.removeLike(findById(id), userService.findById(userId))) {
            log.info("User with id={} unliked film with id={}", userId, id);
        }
    }

    @Override
    public Collection<Film> findTop(Integer count) {
        return filmStorage.findTop(count);
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
