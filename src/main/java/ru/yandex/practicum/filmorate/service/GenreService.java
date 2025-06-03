package ru.yandex.practicum.filmorate.service;

import java.util.Collection;

import ru.yandex.practicum.filmorate.model.Genre;

public interface GenreService {

    Collection<Genre> findAll();

    Genre findById(Integer id);

}
