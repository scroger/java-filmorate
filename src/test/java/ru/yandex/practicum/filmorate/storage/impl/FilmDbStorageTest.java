package ru.yandex.practicum.filmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.mapper.FilmListRowMapper;
import ru.yandex.practicum.filmorate.storage.mapper.FilmRowMapper;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmDbStorage.class, FilmRowMapper.class, FilmListRowMapper.class})
class FilmDbStorageTest {

    private final FilmDbStorage filmStorage;

    Genre genre = Genre.builder()
            .id(1)
            .build();

    private final Film film1 = Film.builder()
            .name("Test film1")
            .description("Test desc1")
            .releaseDate(LocalDate.of(2021, 2, 23))
            .duration(100L)
            .mpa(MpaRating.builder()
                    .id(1)
                    .build())
            .genres(List.of(genre))
            .build();

    private final Film film2 = Film.builder()
            .name("Test film2")
            .description("Test desc2")
            .releaseDate(LocalDate.of(2025, 3, 8))
            .duration(120L)
            .mpa(MpaRating.builder()
                    .id(2)
                    .build())
            .genres(List.of(genre))
            .build();

    @Test
    public void testFindAll() {
        filmStorage.create(film1);
        filmStorage.create(film2);

        Collection<Film> films = filmStorage.findAll();
        Assertions.assertEquals(2, films.size());
    }

    @Test
    public void testFindById() {
        Film created = filmStorage.create(film1);
        Film film = filmStorage.findById(created.getId());

        Assertions.assertNotNull(film);
        Assertions.assertEquals(created.getName(), film.getName());
    }

    @Test
    public void testCreate() {
        Film film = filmStorage.create(film1);
        Assertions.assertNotNull(film.getId());

        Collection<Film> films = filmStorage.findAll();
        Assertions.assertEquals(1, films.size());
    }

    @Test
    public void testUpdate() {
        Film created = filmStorage.create(film1);
        created.setName("TestFilmUpdated");

        filmStorage.update(created);
        Film updated = filmStorage.findById(created.getId());

        Assertions.assertNotNull(updated);
        Assertions.assertEquals("TestFilmUpdated", updated.getName());
    }

}