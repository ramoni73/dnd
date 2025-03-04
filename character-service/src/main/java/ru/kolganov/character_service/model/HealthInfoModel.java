package ru.kolganov.character_service.model;

public record HealthInfoModel(
        HitPointsModel hitPoints,
        HitDiceModel hitDice,
        DeathSavesModel deathSaves
) {
}
