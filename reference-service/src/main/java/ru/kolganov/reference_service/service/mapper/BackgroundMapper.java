package ru.kolganov.reference_service.service.mapper;

import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;
import ru.kolganov.reference_service.entity.*;
import ru.kolganov.reference_service.rest.dto.AbilityDto;
import ru.kolganov.reference_service.rest.dto.FeatDto;
import ru.kolganov.reference_service.rest.dto.background.BackgroundCreateRqDto;
import ru.kolganov.reference_service.rest.dto.background.BackgroundRsDto;
import ru.kolganov.reference_service.rest.dto.SkillDto;
import ru.kolganov.reference_service.rest.dto.background.BackgroundUpdateRqDto;
import ru.kolganov.reference_service.service.model.AbilityModel;
import ru.kolganov.reference_service.service.model.BackgroundModel;
import ru.kolganov.reference_service.service.model.FeatModel;
import ru.kolganov.reference_service.service.model.SkillModel;

import java.util.Optional;
import java.util.stream.Collectors;

@UtilityClass
public class BackgroundMapper {
    public BackgroundModel toModel(@NonNull final BackgroundEntity entity) {
        return new BackgroundModel(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                new FeatModel(
                        entity.getFeatEntity().getCode(),
                        entity.getFeatEntity().getName(),
                        entity.getFeatEntity().getCategory().name()
                ),
                entity.getAbilities().stream()
                        .map(m -> new AbilityModel(m.getCode().name(), m.getName(), m.getDescription()))
                        .collect(Collectors.toSet()),
                entity.getSkillEntities().stream()
                        .map(m -> new SkillModel(m.getCode(), m.getName(), m.getDescription()))
                        .collect(Collectors.toSet()),
                entity.getEquipment(),
                entity.getInstruments()
        );
    }

    public BackgroundRsDto toDto(@NonNull final BackgroundModel model) {
        return new BackgroundRsDto(
                model.code(),
                model.name(),
                model.description(),
                new FeatDto(model.feat().code(), model.feat().name(), model.feat().category()),
                model.abilities().stream()
                        .map(m -> new AbilityDto(m.code(), m.name(), m.description()))
                        .collect(Collectors.toSet()),
                model.skills().stream()
                        .map(m -> new SkillDto(m.code(), m.name(), m.description()))
                        .collect(Collectors.toSet()),
                model.equipment(),
                model.instruments()
        );
    }

    public BackgroundModel toModel(@NonNull final BackgroundCreateRqDto dto) {
        return new BackgroundModel(
                dto.code(),
                dto.name(),
                dto.description(),
                new FeatModel(dto.featCode()),
                dto.abilityCodes().stream().map(AbilityModel::new).collect(Collectors.toSet()),
                dto.skillCodes().stream().map(SkillModel::new).collect(Collectors.toSet()),
                dto.equipment(),
                dto.instruments()
        );
    }

    public BackgroundModel toModel(@NonNull final String code, @NonNull final BackgroundUpdateRqDto dto) {
        return new BackgroundModel(
                code,
                dto.name(),
                dto.description(),
                new FeatModel(dto.featCode()),
                Optional.ofNullable(dto.abilityCodes())
                        .map(m -> m.stream().map(AbilityModel::new).collect(Collectors.toSet()))
                        .orElse(null),
                Optional.ofNullable(dto.skillCodes())
                        .map(m -> m.stream().map(SkillModel::new).collect(Collectors.toSet()))
                        .orElse(null),
                dto.equipment(),
                dto.instruments()
        );
    }
}
