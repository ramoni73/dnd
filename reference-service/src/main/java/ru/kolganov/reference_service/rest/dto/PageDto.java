package ru.kolganov.reference_service.rest.dto;

import java.util.Set;

public record PageDto(
        Integer pageNumber,
        Integer pageSize,
        String sortDirection,
        Set<String> sortFields
) {
}
