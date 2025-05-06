package ru.yandex.practicum.filmorate.service;

import java.util.Collection;

import ru.yandex.practicum.filmorate.model.Film;

public interface FilmService {

    Collection<Film> findAll();

    Film findById(Long id);

    Film create(Film filmRequest);

    Film update(Film filmRequest);

    void addLike(Long id, Long userId);

    void removeLike(Long id, Long userId);

    Collection<Film> findTop(Integer count);

}
