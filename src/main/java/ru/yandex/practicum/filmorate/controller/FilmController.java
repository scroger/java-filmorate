package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.FilmDTO;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public Collection<FilmDTO> getFilms() {
        return filmService.findAll();
    }

    @GetMapping("/{id}")
    public FilmDTO getFilm(@PathVariable("id") @Valid @Positive Long id) {
        return filmService.findById(id);
    }

    @PostMapping
    public FilmDTO createFilm(@RequestBody @Valid Film filmRequest) {
        return filmService.create(filmRequest);
    }

    @PutMapping
    public FilmDTO updateFilm(@RequestBody @Valid Film filmRequest) {
        return filmService.update(filmRequest);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable("id") @Valid @Positive Long id,
                        @PathVariable("userId") @Valid @Positive Long userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable("id") @Valid @Positive Long id,
                           @PathVariable("userId") @Valid @Positive Long userId) {
        filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public Collection<FilmDTO> findTop(@RequestParam(name = "count", required = false, defaultValue = "10") @Valid @Positive Integer count) {
        return filmService.findTop(count);
    }

}
