package ru.kolganov.reference_service.service.mapper;

import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;
import ru.kolganov.reference_service.entity.CharacterClassEntity;
import ru.kolganov.reference_service.rest.dto.CharacterClassDto;
import ru.kolganov.reference_service.rest.dto.CharacterClassPropertyDto;
import ru.kolganov.reference_service.service.model.CharacterClassModel;
import ru.kolganov.reference_service.service.model.CharacterClassPropertyModel;

@UtilityClass
public class CharacterClassMapper {
    public CharacterClassModel toModel(@NonNull final CharacterClassEntity entity) {
        return new CharacterClassModel(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getProperties().stream()
                        .map(m -> new CharacterClassPropertyModel(m.getName(), m.getValue()))
                        .toList()
        );
    }

    public CharacterClassDto toDto(@NonNull final CharacterClassModel model) {
        return new CharacterClassDto(
                model.code(),
                model.name(),
                model.description(),
                model.properties().stream()
                        .map(m -> new CharacterClassPropertyDto(m.name(), m.value()))
                        .toList()
        );
    }
}
