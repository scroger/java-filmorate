package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dto.MpaRatingDTO;

import java.util.Collection;

public interface MpaRatingService {

    Collection<MpaRatingDTO> findAll();

    MpaRatingDTO findById(Integer id);

}
