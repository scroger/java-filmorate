package ru.yandex.practicum.filmorate.mapper;

import ru.yandex.practicum.filmorate.dto.MpaRatingDTO;
import ru.yandex.practicum.filmorate.model.MpaRating;

public class MpaRatingMapper {
    public static MpaRatingDTO map(MpaRating mpaRating) {
        return new MpaRatingDTO(mpaRating.getId(), mpaRating.getTitle());
    }
}
