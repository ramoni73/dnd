package ru.kolganov.reference_service.service.model;

public record SkillModel(
        String code,
        String name,
        String description
) {
    public SkillModel(String code) {
        this(code, null, null);
    }
}
