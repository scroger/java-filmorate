package ru.yandex.practicum.filmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.MpaRatingStorage;

import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MpaRatingDbStorage implements MpaRatingStorage {

    private static final String FIND_ALL_SQL = "SELECT * FROM mpa_ratings";
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + " WHERE id=?";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<MpaRating> mpaRatingRowMapper;

    @Override
    public Collection<MpaRating> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, mpaRatingRowMapper);
    }

    @Override
    public Optional<MpaRating> findById(Integer id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_SQL, mpaRatingRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

}
