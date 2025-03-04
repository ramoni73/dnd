package ru.kolganov.character_service.model;

public record SkillModel(
        String code,
        String name,
        Integer value,
        String proficiencyState
) {
}
