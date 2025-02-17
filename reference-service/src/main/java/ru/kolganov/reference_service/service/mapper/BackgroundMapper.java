package ru.kolganov.reference_service.service.mapper;

import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;
import ru.kolganov.reference_service.entity.BackgroundEntity;
import ru.kolganov.reference_service.entity.BackgroundEquipmentEntity;
import ru.kolganov.reference_service.entity.BackgroundInstrumentEntity;
import ru.kolganov.reference_service.model.AbilityModel;
import ru.kolganov.reference_service.model.BackgroundModel;
import ru.kolganov.reference_service.model.FeatModel;
import ru.kolganov.reference_service.model.SkillModel;

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
                entity.getEquipment().stream()
                        .map(BackgroundEquipmentEntity::getValue)
                        .collect(Collectors.toSet()),
                entity.getInstruments().stream()
                        .map(BackgroundInstrumentEntity::getValue)
                        .collect(Collectors.toSet())
        );
    }
}
