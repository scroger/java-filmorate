package ru.yandex.practicum.filmorate.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FilmControllerTest {

    @Autowired
    private FilmController controller;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void testCreateFilm() {
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"TestFilm\",\"description\":\"test Description\",\"duration\":100," +
                                "\"mpa\":{\"id\":1},\"genres\":[{\"id\":1},{\"id\":2}]," +
                                "\"releaseDate\":\"2011-05-12\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void testCreateFilmWithoutName() {
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"test Description\",\"duration\":100,\"mpa\":{\"id\":1}," +
                                "\"genres\":[{\"id\":1},{\"id\":2}],\"releaseDate\":\"2011-05-12\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void testCreateFilmWithInvalidDescription() {
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(("{\"name\":\"TestFilm\",\"description\":%s,\"duration\":100,\"mpa\":{\"id\":1}," +
                                "\"genres\":[{\"id\":1},{\"id\":2}],\"releaseDate\":\"2011-05-12\"}")
                                .formatted("test".repeat(255))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void testCreateFilmWithInvalidDuration() {
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"TestFilm\",\"description\":\"test\",\"duration\":0,\"mpa\":{\"id\":1}," +
                                "\"genres\":[{\"id\":1},{\"id\":2}],\"releaseDate\":\"2011-05-12\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void testCreateFilmWithInvalidReleaseDate() {
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"TestFilm\",\"description\":\"test\",\"duration\":120,\"mpa\":{\"id\":1}," +
                                "\"genres\":[{\"id\":1},{\"id\":2}],\"releaseDate\":\"1289-05-12\"}"))
                .andExpect(status().isBadRequest());
    }

}