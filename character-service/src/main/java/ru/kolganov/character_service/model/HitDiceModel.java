package ru.kolganov.character_service.model;

public record HitDiceModel(
        String type,
        Integer spent,
        Integer max
) {
}
