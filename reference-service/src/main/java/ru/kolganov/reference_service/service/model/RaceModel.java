package ru.kolganov.reference_service.service.model;

import java.util.List;

public record RaceModel(
        String code,
        String name,
        String description,
        String creatureType,
        String creatureSize,
        Integer speed,
        List<SubRaceModel> subRaces,
        List<RaceSpecialTraitModel> specialTraits
) {
}
