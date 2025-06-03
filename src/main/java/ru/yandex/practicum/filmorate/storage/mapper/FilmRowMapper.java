package ru.yandex.practicum.filmorate.storage.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

@Component
public class FilmRowMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = null;

        do {
            if (null == film) {
                film = Film.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .releaseDate(rs.getDate("release_date").toLocalDate())
                        .duration(rs.getLong("duration"))
                        .likes(rs.getInt("likes"))
                        .genres(new HashSet<>())
                        .build();
            }

            int genreId = rs.getInt("genre_id");
            if (genreId > 0) {
                film.getGenres().add(Genre.builder()
                                .id(genreId)
                                .title(rs.getString("genre_title"))
                        .build());
            }

            if (null == film.getMpa()) {
                int mpaRatingId = rs.getInt("mpa_rating_id");

                if (mpaRatingId > 0) {
                    film.setMpa(MpaRating.builder()
                            .id(mpaRatingId)
                            .title(rs.getString("mpa_rating_title"))
                            .build());
                }
            }
        } while (rs.next());

        return film;
    }

}
