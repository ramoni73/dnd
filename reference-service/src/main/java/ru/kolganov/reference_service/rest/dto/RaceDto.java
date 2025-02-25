package ru.kolganov.reference_service.rest.dto;

import java.util.List;

public record RaceDto(
        String code,
        String name,
        String description,
        String creatureType,
        String creatureSize,
        Integer speed,
        List<SubRaceDto> subRaces,
        List<RaceSpecialTraitDto> specialTraits
) {
}
