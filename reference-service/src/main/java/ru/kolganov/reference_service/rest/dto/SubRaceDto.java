package ru.kolganov.reference_service.rest.dto;

import java.util.List;

public record SubRaceDto(
        String code,
        String name,
        String description,
        List<SubRacePropertyDto> properties
) {
}
