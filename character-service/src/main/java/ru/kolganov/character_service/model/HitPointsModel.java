package ru.kolganov.character_service.model;

public record HitPointsModel(
        Integer current,
        Integer temp,
        Integer max
) {
}
