package ru.kolganov.reference_service.service.model;

public record FeatModel(
        String code,
        String name,
        String category
) {
    public FeatModel(String code) {
        this(code, null, null);
    }
}
