package ru.kolganov.character_service.model.main_stats;

public record SkillModel(
        String code,
        String name,
        Integer value,
        String proficiencyState
) {
}
