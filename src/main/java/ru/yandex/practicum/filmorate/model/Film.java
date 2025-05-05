package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validation.MinDate;

import java.time.LocalDate;

/**
 * Film.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Film {
    private Long id;

    @NotBlank
    private String name;

    @Size(max = 200)
    private String description;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @MinDate(value = "1895-02-28", message = "Release date should be after 1895-02-28")
    private LocalDate releaseDate;

    @Positive
    private Long duration;

    @PositiveOrZero
    private int likes;
}
