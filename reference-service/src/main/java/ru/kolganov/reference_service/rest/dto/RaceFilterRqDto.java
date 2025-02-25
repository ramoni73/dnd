package ru.kolganov.reference_service.rest.dto;

import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;

public record RaceFilterRqDto(
        @Nullable
        @Size(max = 100, message = "Name must not exceed 255 characters")
        String name,

        @Nullable
        @Size(max = 500, message = "Description must not exceed 1000 characters")
        String description,

        @Nullable
        PageDto pageDto
) {
}
