package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Collection<Film> findAll();

    Film findById(Long id);

    void save(Film film);

    Collection<Film> findTop(Integer count);

}
