package ru.yandex.practicum.filmorate.service.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.dto.MpaRatingDTO;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.MpaRatingMapper;
import ru.yandex.practicum.filmorate.service.MpaRatingService;
import ru.yandex.practicum.filmorate.storage.MpaRatingStorage;

@Service
@RequiredArgsConstructor
public class MpaRatingServiceImpl implements MpaRatingService {

    private final MpaRatingStorage mpaRatingStorage;

    @Override
    public Collection<MpaRatingDTO> findAll() {
        return mpaRatingStorage.findAll().stream().map(MpaRatingMapper::map).toList();
    }

    @Override
    public MpaRatingDTO findById(Integer id) {
        return MpaRatingMapper.map(mpaRatingStorage.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Mpa rating with id=%d not found", id))));
    }

}
