package ru.kolganov.reference_service.event.model.kafka;

import java.time.Instant;
import java.util.UUID;

public record BackgroundEvent(
        UUID eventId,
        String eventType,
        Instant occurredAt,
        BackgroundEventPayload payload
) {
    public BackgroundEvent(String eventType, BackgroundEventPayload payload) {
        this(UUID.randomUUID(), eventType, Instant.now(), payload);
    }
}
