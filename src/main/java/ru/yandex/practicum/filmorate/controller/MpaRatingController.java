package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.MpaRatingService;

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaRatingController {

    private final MpaRatingService mpaRatingService;

    @GetMapping
    public Collection<MpaRating> findAll() {
        return mpaRatingService.findAll();
    }

    @GetMapping("/{ratingId}")
    public MpaRating getFilmById(@PathVariable Integer ratingId) {
        return mpaRatingService.findById(ratingId);
    }

}
