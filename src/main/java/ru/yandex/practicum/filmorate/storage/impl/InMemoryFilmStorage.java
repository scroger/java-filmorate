package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

@Component("inMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    @Override
    public Film findById(Long id) {
        return Optional.ofNullable(films.get(id))
                .orElseThrow(() -> new NotFoundException(String.format("Film with id=%d not found.", id)));
    }

    @Override
    public Film create(Film film) {
        film.setId(generateId());

        return save(film);
    }

    @Override
    public Film update(Film film) {
        return save(film);
    }

    private Film save(Film film) {
        films.put(film.getId(), film);

        return film;
    }

    @Override
    public Collection<Film> findTop(Integer count) {
        List<Film> sortedFilms = findAll().stream()
                .sorted(Comparator.comparingInt(Film::getLikes).reversed())
                .toList();

        return sortedFilms.subList(0, count > sortedFilms.size() ? sortedFilms.size() : count);
    }

    @Override
    public void addLike(Film film, User user) {
        film.setLikes(film.getLikes() + 1);

        save(film);
    }

    @Override
    public boolean removeLike(Film film, User user) {
        if (film.getLikes() > 0) {
            film.setLikes(film.getLikes() - 1);

            save(film);

            return true;
        }

        return false;
    }

    private long generateId() {
        long maxId = findAll()
                .stream()
                .mapToLong(Film::getId)
                .max()
                .orElse(0);

        return ++maxId;
    }

}
