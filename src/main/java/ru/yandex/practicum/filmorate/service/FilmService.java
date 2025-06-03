package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dto.FilmDTO;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmService {

    Collection<FilmDTO> findAll();

    FilmDTO findById(Long id);

    FilmDTO create(Film filmRequest);

    FilmDTO update(Film filmRequest);

    void addLike(Long id, Long userId);

    void removeLike(Long id, Long userId);

    Collection<FilmDTO> findTop(Integer count);

}
