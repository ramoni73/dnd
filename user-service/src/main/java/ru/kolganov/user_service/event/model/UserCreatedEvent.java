package ru.kolganov.user_service.event.model;

import java.time.Instant;
import java.util.UUID;

public record UserCreatedEvent(
        UUID eventId,
        String eventType,
        Instant occurredAt,
        UserCreatedPayload payload
) {
    public UserCreatedEvent(UserCreatedPayload payload) {
        this(UUID.randomUUID(), "UserCreated", Instant.now(), payload);
    }
}
