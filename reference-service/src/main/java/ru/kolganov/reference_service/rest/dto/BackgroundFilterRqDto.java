package ru.kolganov.reference_service.rest.dto;

import org.springframework.data.domain.Pageable;
import jakarta.validation.constraints.Size;

public record BackgroundFilterRqDto(
        @Size(max = 100, message = "Name must not exceed 255 characters")
        String name,

        @Size(max = 500, message = "Description must not exceed 1000 characters")
        String description,

        Pageable pageable
) {
}
