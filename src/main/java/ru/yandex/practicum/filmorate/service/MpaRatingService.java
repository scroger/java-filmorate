package ru.yandex.practicum.filmorate.service;

import java.util.Collection;

import ru.yandex.practicum.filmorate.model.MpaRating;

public interface MpaRatingService {

    Collection<MpaRating> findAll();

    MpaRating findById(Integer id);

}
