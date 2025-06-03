package ru.yandex.practicum.filmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.mapper.GenreRowMapper;

import java.util.Collection;
import java.util.Optional;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({GenreDbStorage.class, GenreRowMapper.class})
class GenreDbStorageTest {

    private final GenreDbStorage genreStorage;

    @Test
    public void testFindAll() {
        Collection<Genre> genres = genreStorage.findAll();

        Assertions.assertEquals(6, genres.size());
    }

    @Test
    public void testFindById() {
        Optional<Genre> optGenre = genreStorage.findById(1);

        Assertions.assertNotNull(optGenre);
        Assertions.assertTrue(optGenre.isPresent());
        Assertions.assertEquals("Комедия", optGenre.get().getTitle());
    }

}