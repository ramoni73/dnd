package ru.kolganov.user_service.rest.dto;

import java.util.List;
import java.util.UUID;

public record UserDtoRs(
        UUID userId,
        List<String> roles
) {
}
