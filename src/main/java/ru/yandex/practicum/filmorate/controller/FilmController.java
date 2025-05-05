package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public Collection<Film> getFilms() {
        return filmService.findAll();
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable @Valid @Positive Long id) {
        return filmService.findById(id);
    }

    @PostMapping
    public Film createFilm(@RequestBody @Valid Film filmRequest) {
        return filmService.create(filmRequest);
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film filmRequest) {
        return filmService.update(filmRequest);
    }

    //PUT /films/{id}/like/{userId}
    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable @Valid @Positive Long id,
                        @PathVariable @Valid @Positive Long userId) {
        filmService.addLike(id, userId);
    }

    //DELETE /films/{id}/like/{userId}
    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable @Valid @Positive Long id,
                           @PathVariable @Valid @Positive Long userId) {
        filmService.removeLike(id, userId);
    }

    //GET /films/popular?count={count}
    @GetMapping("/popular?count={count}")
    public Collection<Film> findTop(@RequestParam @Valid @Positive Integer count) {
        return filmService.findTop(count);
    }

}
