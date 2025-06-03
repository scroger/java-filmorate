package ru.yandex.practicum.filmorate.storage.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

@Primary
@Repository("filmDbStorage")
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final static String FIND_ALL_SQL = """
            SELECT
                f.id,
                f.name,
                f.description,
                f.release_date,
                f.duration,
                (SELECT COUNT(fl.film_id) FROM film_likes fl WHERE fl.film_id = f.id) likes,
                g.id genre_id,
                g.title genre_title,
                mr.id mpa_rating_id,
                mr.title mpa_rating_title
            FROM films f
            LEFT JOIN film_genres fg ON f.id = fg.film_id
            LEFT JOIN genres g ON fg.genre_id = g.id
            LEFT JOIN mpa_ratings mr ON f.mpa_rating_id = mr.id""";

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + " WHERE f.id=?";

    private final static String FIND_TOP_SQL = FIND_ALL_SQL + " ORDER BY likes DESC LIMIT ?";

    private final static String CREATE_SQL = """
            INSERT INTO films (name, description, release_date, duration, mpa_rating_id) VALUES (?, ?, ?, ?, ?)""";

    private final static String UPDATE_SQL = """
            UPDATE films SET name=?, description=?, release_date=?, duration=?, mpa_rating_id=? WHERE id=?""";

    private final static String ADD_GENRE_SQL = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)";

    private final static String REMOVE_GENRES_SQL = "DELETE FROM film_genres WHERE film_id=?";

    private final static String ADD_LIKE_SQL = "INSERT INTO film_likes (film_id, user_id) VALUES  (?, ?)";

    private final static String LIKES_COUNT_SQL = "SELECT count(film_id) FROM film_likes WHERE film_id=? AND user_id=?";

    private final static String REMOVE_LIKE_SQL = "DELETE FROM film_likes WHERE film_id=? AND user_id=?";

    private final static String DELETE_SQL = "DELETE FROM films WHERE id=?";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Film> filmRowMapper;
    private final RowMapper<List<Film>> filmListRowMapper;

    @Override
    public Collection<Film> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, filmListRowMapper).getFirst();
    }

    @Override
    public Optional<Film> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_SQL, filmRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Film create(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement stmt = con.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setLong(4, film.getDuration());
            if (null != film.getMpaRating()) {
                stmt.setLong(5, film.getMpaRating().getId());
            } else {
                stmt.setNull(5, Types.NULL);
            }

            return stmt;
        }, keyHolder);

        film.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        if (null != film.getGenres()) {
            jdbcTemplate.batchUpdate(
                    ADD_GENRE_SQL,
                    film.getGenres(),
                    film.getGenres().size(),
                    (PreparedStatement ps, Genre genre) -> {
                        ps.setLong(1, film.getId());
                        ps.setLong(2, genre.getId());
                    }
            );
        }

        return film;
    }

    @Override
    public Film update(Film film) {
        jdbcTemplate.update(
                UPDATE_SQL,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                Optional.ofNullable(film.getMpaRating()).map(MpaRating::getId).orElse(null),
                film.getId()
        );

        jdbcTemplate.update(REMOVE_GENRES_SQL, film.getId());

        if (null != film.getGenres()) {
            jdbcTemplate.batchUpdate(
                    ADD_GENRE_SQL,
                    film.getGenres(),
                    film.getGenres().size(),
                    (PreparedStatement ps, Genre genre) -> {
                        ps.setLong(1, film.getId());
                        ps.setLong(2, genre.getId());
                    }
            );
        }

        return film;
    }

    @Override
    public Collection<Film> findTop(Integer count) {
        return jdbcTemplate.query(FIND_TOP_SQL, ps -> ps.setInt(1, count), filmListRowMapper).getFirst();
    }

    @Override
    public void addLike(Film film, User user) {
        Integer likesCount = jdbcTemplate.queryForObject(
                LIKES_COUNT_SQL,
                Integer.class,
                film.getId(),
                user.getId()
        );

        if (null == likesCount || 0 == likesCount) {
            jdbcTemplate.update(
                    ADD_LIKE_SQL,
                    film.getId(),
                    user.getId()
            );
        }
    }

    @Override
    public boolean removeLike(Film film, User user) {
        return jdbcTemplate.update(
                REMOVE_LIKE_SQL,
                film.getId(),
                user.getId()
        ) > 0;
    }

    public boolean delete(Long id) {
        return jdbcTemplate.update(DELETE_SQL, id) > 0;
    }

}
