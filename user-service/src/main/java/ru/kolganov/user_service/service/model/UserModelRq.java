package ru.kolganov.user_service.service.model;

public record UserModelRq(
        String externalId,
        IdentityProvider provider
) {
}
