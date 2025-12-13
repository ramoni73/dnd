package ru.kolganov.user_service.event.model;

import ru.kolganov.user_service.service.model.IdentityProvider;

import java.util.UUID;

public record UserCreatedPayload(
        UUID userId,
        IdentityProvider provider,
        String externalId
) {
}
