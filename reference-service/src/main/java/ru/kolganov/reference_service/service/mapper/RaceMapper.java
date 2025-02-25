package ru.kolganov.reference_service.service.mapper;

import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;
import ru.kolganov.reference_service.entity.RaceEntity;
import ru.kolganov.reference_service.rest.dto.RaceDto;
import ru.kolganov.reference_service.rest.dto.RaceSpecialTraitDto;
import ru.kolganov.reference_service.rest.dto.SubRaceDto;
import ru.kolganov.reference_service.rest.dto.SubRacePropertyDto;
import ru.kolganov.reference_service.service.model.RaceModel;
import ru.kolganov.reference_service.service.model.RaceSpecialTraitModel;
import ru.kolganov.reference_service.service.model.SubRaceModel;
import ru.kolganov.reference_service.service.model.SubRacePropertyModel;

@UtilityClass
public class RaceMapper {
    public RaceModel toModel(@NonNull final RaceEntity entity) {
        return new RaceModel(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getCreatureType(),
                entity.getCreatureSize(),
                entity.getSpeed(),
                entity.getSubRaceEntities().stream()
                        .map(m -> new SubRaceModel(
                                m.getCode(),
                                m.getName(),
                                m.getDescription(),
                                m.getProperties().stream()
                                        .map(p -> new SubRacePropertyModel(p.getName(), p.getValue()))
                                        .toList()
                        ))
                        .toList(),
                entity.getSpecialTraits().stream()
                        .map(m -> new RaceSpecialTraitModel(m.getName(), m.getValue()))
                        .toList()
        );
    }

    public RaceDto toDto(@NonNull final RaceModel model) {
        return new RaceDto(
                model.code(),
                model.name(),
                model.description(),
                model.creatureType(),
                model.creatureSize(),
                model.speed(),
                model.subRaces().stream()
                        .map(m -> new SubRaceDto(
                                m.code(),
                                m.name(),
                                m.description(),
                                m.properties().stream()
                                        .map(p -> new SubRacePropertyDto(p.name(), p.value()))
                                        .toList()
                        ))
                        .toList(),
                model.specialTraits().stream()
                        .map(m -> new RaceSpecialTraitDto(m.name(), m.value()))
                        .toList()
        );
    }
}
