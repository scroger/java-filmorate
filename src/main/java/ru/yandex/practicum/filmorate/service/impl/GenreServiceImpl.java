package ru.yandex.practicum.filmorate.service.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.dto.GenreDTO;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.storage.GenreStorage;


@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreStorage genreStorage;

    @Override
    public Collection<GenreDTO> findAll() {
        return genreStorage.findAll().stream().map(GenreMapper::map).toList();
    }

    @Override
    public GenreDTO findById(Integer id) {
        return GenreMapper.map(genreStorage.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Genre with id=%d not found", id))));
    }
}
