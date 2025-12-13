package ru.kolganov.user_service.rest.dto;

public record UserDtoRq(
        String externalId,
        String provider
) {
}
