package ru.yandex.practicum.filmorate.storage.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;

@Component
public class FilmListRowMapper implements RowMapper<List<Film>> {

    @Override
    public List<Film> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<Long, Film> films = new HashMap<>();

        while (rs.next()) {
            Long filmId = rs.getLong("id");
            Film film = films.get(filmId);

            if (null == film) {
                film = Film.builder()
                        .id(filmId)
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .releaseDate(rs.getDate("release_date").toLocalDate())
                        .duration(rs.getLong("duration"))
                        .likes(rs.getInt("likes"))
                        .genres(new HashSet<>())
                        .build();

                films.put(filmId, film);
            }

            int genreId = rs.getInt("genre_id");
            if (genreId > 0) {
                film.getGenres().add(Genre.builder()
                                .id(genreId)
                                .title(rs.getString("genre_title"))
                        .build());
            }

            if (null == film.getMpaRating()) {
                int mpaRatingId = rs.getInt("mpa_rating_id");

                if (mpaRatingId > 0) {
                    film.setMpaRating(MpaRating.builder()
                            .id(mpaRatingId)
                            .title(rs.getString("mpa_rating_title"))
                            .build());
                }
            }
        }

        return films.values().stream().toList();
    }

}
