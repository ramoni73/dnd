package ru.kolganov.reference_service.rest.dto.background;

import jakarta.validation.constraints.Size;

import java.util.Set;

public record BackgroundUpdateRqDto(
        String name,
        String description,
        String featCode,

        @Size(min = 3, max = 3, message = "must contain exactly 3 elements")
        Set<String> abilityCodes,

        @Size(min = 2, max = 2, message = "must contain exactly 2 element")
        Set<String> skillCodes,

        String equipment,
        String instruments
) {
}
