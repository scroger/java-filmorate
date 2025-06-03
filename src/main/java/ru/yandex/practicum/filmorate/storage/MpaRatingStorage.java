package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;
import java.util.Optional;

import ru.yandex.practicum.filmorate.model.MpaRating;

public interface MpaRatingStorage {

    Collection<MpaRating> findAll();

    Optional<MpaRating> findById(Integer id);

}
