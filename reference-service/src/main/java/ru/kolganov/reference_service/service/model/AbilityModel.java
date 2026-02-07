package ru.kolganov.reference_service.service.model;

public record AbilityModel(
        String code,
        String name,
        String description
) {
    public AbilityModel(String code) {
        this(code, null, null);
    }
}
