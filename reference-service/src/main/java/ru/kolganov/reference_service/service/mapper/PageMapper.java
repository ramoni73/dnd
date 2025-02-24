package ru.kolganov.reference_service.service.mapper;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.kolganov.reference_service.rest.dto.PageDto;
import ru.kolganov.reference_service.rest.dto.PageDtoRs;

import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@UtilityClass
public class PageMapper {
    public Pageable toPageable(@Nullable final PageDto pageDto) {
        return Optional.ofNullable(pageDto)
                .map(page -> PageRequest.of(
                        Optional.ofNullable(page.pageNumber()).orElse(0),
                        Optional.ofNullable(page.pageSize()).orElse(10),
                        Sort.by(
                                Optional.of(Sort.Direction.fromString(page.sortDirection().toUpperCase(Locale.ROOT)))
                                        .orElse(Sort.Direction.ASC),
                                Optional.of(String.join(", ", page.sortFields()))
                                        .orElse("name")
                        )
                ))
                .orElse(PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name")));
    }

    public <T> PageDtoRs<T> toPageDtoRs(@NonNull final Page<T> page) {
        return new PageDtoRs<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements()
        );
    }
}
