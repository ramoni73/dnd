package ru.kolganov.reference_service.rest.dto.background;

import ru.kolganov.reference_service.rest.dto.AbilityDto;
import ru.kolganov.reference_service.rest.dto.FeatDto;
import ru.kolganov.reference_service.rest.dto.SkillDto;

import java.util.Set;

public record BackgroundRsDto(
        String code,
        String name,
        String description,
        FeatDto featDto,
        Set<AbilityDto> abilities,
        Set<SkillDto> skills,
        String equipment,
        String instruments
) {
}
