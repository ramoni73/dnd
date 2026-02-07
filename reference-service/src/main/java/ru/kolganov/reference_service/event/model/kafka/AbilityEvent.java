package ru.kolganov.reference_service.event.model.kafka;

public record AbilityEvent(
        String code,
        String name,
        String description
) {
}
