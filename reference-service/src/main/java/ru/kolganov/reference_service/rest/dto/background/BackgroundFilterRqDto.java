package ru.kolganov.reference_service.rest.dto.background;

import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;
import ru.kolganov.reference_service.rest.dto.PageDto;

public record BackgroundFilterRqDto(
        @Nullable
        @Size(max = 100, message = "Name must not exceed 100 characters")
        String name,

        @Nullable
        @Size(max = 500, message = "Description must not exceed 500 characters")
        String description,

        @Nullable
        PageDto pageDto
) {
}
