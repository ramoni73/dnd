package ru.kolganov.reference_service.rest.dto;

import java.util.List;

public record CharacterClassDto(
        String code,
        String name,
        String description,
        List<CharacterClassPropertyDto> properties
) {
}
