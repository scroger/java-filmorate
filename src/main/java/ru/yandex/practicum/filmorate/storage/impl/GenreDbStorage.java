package ru.yandex.practicum.filmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {

    private static final String FIND_ALL_SQL = "SELECT * FROM genres";
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + " WHERE id=?";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Genre> genreRowMapper;

    @Override
    public Collection<Genre> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, genreRowMapper);
    }

    @Override
    public Optional<Genre> findById(Integer id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_SQL, genreRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

}
