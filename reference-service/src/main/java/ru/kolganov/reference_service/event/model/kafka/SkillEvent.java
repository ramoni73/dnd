package ru.kolganov.reference_service.event.model.kafka;

public record SkillEvent(
        String code,
        String name,
        String description
) {
}
