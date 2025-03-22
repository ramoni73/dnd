package ru.kolganov.character_service.model.character_info;

public record CharacterInfoModel(
        String characterName,
        BackgroundModel background,
        RaceModel race,
        ClassModel characterClass,
        SubClassModel subClass
) {
}
