package ru.kolganov.reference_service.service.mapper;

import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;
import ru.kolganov.reference_service.entity.*;
import ru.kolganov.reference_service.rest.dto.AbilityDto;
import ru.kolganov.reference_service.rest.dto.BackgroundDto;
import ru.kolganov.reference_service.rest.dto.FeatDto;
import ru.kolganov.reference_service.rest.dto.SkillDto;
import ru.kolganov.reference_service.service.model.AbilityModel;
import ru.kolganov.reference_service.service.model.BackgroundModel;
import ru.kolganov.reference_service.service.model.FeatModel;
import ru.kolganov.reference_service.service.model.SkillModel;

import java.util.stream.Collectors;

@UtilityClass
public class BackgroundMapper {
    public BackgroundModel toModel(@NonNull final BackgroundEntity entity) {
        return new BackgroundModel(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getAbilities().stream()
                        .map(m -> new AbilityModel(m.getCode().name(), m.getName(), m.getDescription()))
                        .collect(Collectors.toSet()),
                entity.getFeatEntities().stream()
                        .map(m -> new FeatModel(m.getCode(), m.getName(), m.getCategory().name()))
                        .collect(Collectors.toSet()),
                entity.getSkillEntities().stream()
                        .map(m -> new SkillModel(m.getCode(), m.getName(), m.getDescription()))
                        .collect(Collectors.toSet()),
                entity.getEquipment(),
                entity.getInstruments()
        );
    }

    public BackgroundDto toDto(@NonNull final BackgroundModel model) {
        return new BackgroundDto(
                model.code(),
                model.name(),
                model.description(),
                model.abilities().stream()
                        .map(m -> new AbilityDto(m.code(), m.name(), m.description()))
                        .collect(Collectors.toSet()),
                model.feats().stream()
                        .map(m -> new FeatDto(m.code(), m.name(), m.category()))
                        .collect(Collectors.toSet()),
                model.skills().stream()
                        .map(m -> new SkillDto(m.code(), m.name(), m.description()))
                        .collect(Collectors.toSet()),
                model.equipment(),
                model.instruments()
        );
    }

    public BackgroundModel toModel(@NonNull final BackgroundDto dto) {
        return new BackgroundModel(
                dto.code(),
                dto.name(),
                dto.description(),
                dto.abilities().stream()
                        .map(m -> new AbilityModel(m.code(), m.name(), m.description()))
                        .collect(Collectors.toSet()),
                dto.feats().stream()
                        .map(m -> new FeatModel(m.code(), m.name(), m.category()))
                        .collect(Collectors.toSet()),
                dto.skills().stream()
                        .map(m -> new SkillModel(m.code(), m.name(), m.description()))
                        .collect(Collectors.toSet()),
                dto.equipment(),
                dto.instruments()
        );
    }
}
