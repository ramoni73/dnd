package ru.kolganov.reference_service.rest.dto;

import java.util.Set;

public record BackgroundDto(
        String code,
        String name,
        String description,
        Set<AbilityDto> abilities,
        Set<FeatDto> feats,
        Set<SkillDto> skills,
        Set<String> backgroundEquipment,
        Set<String> backgroundInstrument
) {
}
