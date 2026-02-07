package ru.kolganov.reference_service.service.model;

import java.util.Set;

public record BackgroundModel(
        String code,
        String name,
        String description,
        FeatModel feat,
        Set<AbilityModel> abilities,
        Set<SkillModel> skills,
        String equipment,
        String instruments
) {
}
