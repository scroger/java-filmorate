package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface FilmStorage {

    Collection<Film> findAll();

    Film findById(Long id);

    Film create(Film film);

    Film update(Film film);

    Collection<Film> findTop(Integer count);

    void addLike(Film film, User user);

    boolean removeLike(Film film, User user);

}
