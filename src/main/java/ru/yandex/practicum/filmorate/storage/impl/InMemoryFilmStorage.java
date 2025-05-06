package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

@Component
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
    public void save(Film film) {
        films.put(film.getId(), film);
    }

    public Collection<Film> findTop(Integer count) {
        List<Film> sortedFilms = findAll().stream()
                .sorted(Comparator.comparingInt(Film::getLikes).reversed())
                .toList();

        return sortedFilms.subList(0, count > sortedFilms.size() ? sortedFilms.size() : count);
    }

}
