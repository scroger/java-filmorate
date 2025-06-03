package ru.yandex.practicum.filmorate.mapper;

import ru.yandex.practicum.filmorate.dto.FilmDTO;
import ru.yandex.practicum.filmorate.model.Film;

public class FilmMapper {
    public static FilmDTO map(Film film) {
        FilmDTO.FilmDTOBuilder builder = FilmDTO.builder()
                .id(film.getId())
                .name(film.getName())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(film.getDuration())
                .likes(film.getLikes());

        if (null != film.getMpa()) {
            builder.mpa(MpaRatingMapper.map(film.getMpa()));
        }

        if (null != film.getGenres()) {
            builder.genres(film.getGenres().stream().map(GenreMapper::map).toList());
        }

        return builder.build();
    }
}
