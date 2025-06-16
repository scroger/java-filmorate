package ru.yandex.practicum.filmorate.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

@Data
@Builder
public class FilmDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Long duration;
    private int likes;
    private MpaRatingDTO mpa;
    private Collection<GenreDTO> genres;
}
