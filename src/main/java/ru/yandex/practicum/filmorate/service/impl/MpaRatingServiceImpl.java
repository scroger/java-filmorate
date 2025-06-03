package ru.yandex.practicum.filmorate.service.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.MpaRatingService;
import ru.yandex.practicum.filmorate.storage.MpaRatingStorage;

@Service
@RequiredArgsConstructor
public class MpaRatingServiceImpl implements MpaRatingService {

    private final MpaRatingStorage mpaRatingStorage;

    @Override
    public Collection<MpaRating> findAll() {
        return mpaRatingStorage.findAll();
    }

    @Override
    public MpaRating findById(Integer id) {
        return mpaRatingStorage.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Mpa rating with id=%d not found", id)));
    }

}
