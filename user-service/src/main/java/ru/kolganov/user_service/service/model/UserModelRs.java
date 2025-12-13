package ru.kolganov.user_service.service.model;

import java.util.List;
import java.util.UUID;

public record UserModelRs(
        UUID userId,
        List<String> roles
) {
}
