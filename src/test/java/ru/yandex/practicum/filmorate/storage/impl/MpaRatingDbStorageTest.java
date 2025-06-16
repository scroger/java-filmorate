package ru.yandex.practicum.filmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.mapper.MpaRatingRowMapper;

import java.util.Collection;
import java.util.Optional;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({MpaRatingDbStorage.class, MpaRatingRowMapper.class})
class MpaRatingDbStorageTest {

    private final MpaRatingDbStorage mpaStorage;

    @Test
    public void testFindAll() {
        Collection<MpaRating> mpaRatings = mpaStorage.findAll();

        Assertions.assertEquals(5, mpaRatings.size());
    }

    @Test
    public void testFindById() {
        Optional<MpaRating> optMpaRating = mpaStorage.findById(1);

        Assertions.assertNotNull(optMpaRating);
        Assertions.assertTrue(optMpaRating.isPresent());
        Assertions.assertEquals("G", optMpaRating.get().getTitle());
    }

}