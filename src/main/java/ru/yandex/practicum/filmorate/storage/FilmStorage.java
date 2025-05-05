package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;
import java.util.Optional;

import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {

    Collection<Film> findAll();

    Optional<Film> findById(Long id);

    void save(Film film);

}
