package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.FilmDTO;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.MpaRatingStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmServiceImpl implements FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;
    private final MpaRatingStorage mpaRatingStorage;
    private final GenreStorage genreStorage;

    @Override
    public Collection<FilmDTO> findAll() {
        return filmStorage.findAll().stream().map(FilmMapper::map).toList();
    }

    @Override
    public FilmDTO findById(Long id) {
        return FilmMapper.map(filmStorage.findById(id));
    }

    @Override
    public FilmDTO create(Film filmRequest) {
        if (null != filmRequest.getMpa()) {
            Optional<MpaRating> optMpaRating = mpaRatingStorage.findById(filmRequest.getMpa().getId());
            if (optMpaRating.isEmpty()) {
                throw new NotFoundException(String.format("Rating with id=%d not found.", filmRequest.getMpa().getId()));
            }

            filmRequest.setMpa(optMpaRating.get());
        }

        if (null != filmRequest.getGenres() && !filmRequest.getGenres().isEmpty()) {
            List<Genre> genres = new ArrayList<>();

            for (Genre genre : filmRequest.getGenres()) {
                Optional<Genre> optGenre = genreStorage.findById(genre.getId());
                if (optGenre.isEmpty()) {
                    throw new NotFoundException(String.format("Genre with id=%d not found.", genre.getId()));
                }
                genres.add(optGenre.get());
            }

            filmRequest.setGenres(genres);
        }

        filmStorage.create(filmRequest);
        log.info("Film successfully created");

        return FilmMapper.map(filmRequest);
    }

    @Override
    public FilmDTO update(Film filmRequest) {
        Long filmId = filmRequest.getId();
        if (null == filmId) {
            throw new ValidationException("Id should be specified");
        }

        if (null != filmRequest.getMpa()) {
            Optional<MpaRating> optMpaRating = mpaRatingStorage.findById(filmRequest.getMpa().getId());
            if (optMpaRating.isEmpty()) {
                throw new NotFoundException(String.format("Rating with id=%d not found.", filmRequest.getMpa().getId()));
            }

            filmRequest.setMpa(optMpaRating.get());
        }

        if (null != filmRequest.getGenres() && !filmRequest.getGenres().isEmpty()) {
            List<Genre> genres = new ArrayList<>();

            for (Genre genre : filmRequest.getGenres()) {
                Optional<Genre> optGenre = genreStorage.findById(genre.getId());
                if (optGenre.isEmpty()) {
                    throw new NotFoundException(String.format("Genre with id=%d not found.", genre.getId()));
                }
                genres.add(optGenre.get());
            }

            filmRequest.setGenres(genres);
        }

        Film film = filmStorage.findById(filmId);
        filmRequest.setLikes(film.getLikes());

        filmStorage.update(filmRequest);
        log.info("Film successfully updated");

        return FilmMapper.map(filmRequest);
    }

    @Override
    public void addLike(Long id, Long userId) {
        filmStorage.addLike(filmStorage.findById(id), userService.findById(userId));

        log.info("User with id={} liked film with id={}", userId, id);
    }

    @Override
    public void removeLike(Long id, Long userId) {
        if (filmStorage.removeLike(filmStorage.findById(id), userService.findById(userId))) {
            log.info("User with id={} unliked film with id={}", userId, id);
        }
    }

    @Override
    public Collection<FilmDTO> findTop(Integer count) {
        return filmStorage.findTop(count).stream().map(FilmMapper::map).toList();
    }

}
