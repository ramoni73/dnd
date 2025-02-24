package ru.kolganov.reference_service.rest.dto;

import java.util.List;

public record PageDtoRs<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long totalElements
) {
}
