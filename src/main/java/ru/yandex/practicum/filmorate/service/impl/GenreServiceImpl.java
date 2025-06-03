package ru.yandex.practicum.filmorate.service.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreStorage genreStorage;

    @Override
    public Collection<Genre> findAll() {
        return genreStorage.findAll();
    }

    @Override
    public Genre findById(Integer id) {
        return genreStorage.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Genre with id=%d not found", id)));
    }
}
