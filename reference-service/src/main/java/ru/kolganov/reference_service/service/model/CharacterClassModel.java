package ru.kolganov.reference_service.service.model;

import java.util.List;

public record CharacterClassModel(
        String code,
        String name,
        String description,
        List<CharacterClassPropertyModel> properties
) {
}
