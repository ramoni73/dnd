package ru.kolganov.character_service.model;

import java.util.List;

public record AbilityModel(
        String code,
        String name,
        Integer score,
        Integer modifier,
        Boolean savingThrow,
        List<SkillModel> skills
) {
}
