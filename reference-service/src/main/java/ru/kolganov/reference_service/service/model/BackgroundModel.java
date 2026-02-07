package ru.kolganov.reference_service.service.model;

import java.util.Set;

public record BackgroundModel(
        String code,
        String name,
        String description,
        Set<AbilityModel> abilities,
        Set<FeatModel> feats,
        Set<SkillModel> skills,
        String equipment,
        String instruments
) {
}
