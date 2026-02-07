package ru.kolganov.reference_service.event.model.kafka;

public record FeatEvent(
        String code,
        String name,
        String category
) {
}
