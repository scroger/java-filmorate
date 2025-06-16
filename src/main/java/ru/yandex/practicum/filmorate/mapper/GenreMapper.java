package ru.yandex.practicum.filmorate.mapper;

import ru.yandex.practicum.filmorate.dto.GenreDTO;
import ru.yandex.practicum.filmorate.model.Genre;

public class GenreMapper {
    public static GenreDTO map(Genre genre) {
        return new GenreDTO(genre.getId(), genre.getTitle());
    }
}
