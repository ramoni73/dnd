package ru.kolganov.reference_service.service.model;

import java.util.List;

public record SubRaceModel(
        String code,
        String name,
        String description,
        List<SubRacePropertyModel> properties
) {
}
