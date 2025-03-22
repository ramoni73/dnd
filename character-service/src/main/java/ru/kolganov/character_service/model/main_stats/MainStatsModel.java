package ru.kolganov.character_service.model.main_stats;

import java.util.List;

public record MainStatsModel(
        Integer proficiencyBonus,
        Boolean heroicInspiration,
        List<AbilityModel> abilities,
        Integer initiative,
        Integer speed,
        Integer size,
        Integer passivePerception
) {
}
