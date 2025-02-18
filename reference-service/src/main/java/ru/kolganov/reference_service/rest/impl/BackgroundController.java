package ru.kolganov.reference_service.rest.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import ru.kolganov.reference_service.exception.InvalidParameterException;
import ru.kolganov.reference_service.rest.dto.BackgroundFilterRqDto;
import ru.kolganov.reference_service.service.model.BackgroundModel;
import ru.kolganov.reference_service.rest.BackgroundApi;
import ru.kolganov.reference_service.service.BackgroundService;
import ru.kolganov.reference_service.service.filter.BackgroundFilter;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BackgroundController implements BackgroundApi {
    private final BackgroundService backgroundService;

    @Override
    public ResponseEntity<BackgroundModel> getByCode(final String code) {
        return Optional.ofNullable(code)
                .map(c -> {
                    log.info("Fetching background by code: {}", c);
                    return ResponseEntity.ok(backgroundService.getByCode(c));
                })
                .orElseThrow(() -> new InvalidParameterException("code", "Background code cannot be null"));
    }

    @Override
    public ResponseEntity<Page<BackgroundModel>> findByFilter(@Valid @NonNull final BackgroundFilterRqDto filter) {
        log.info("Received filter request: name={}, description={}, pageable={}", filter.name(), filter.description(), filter.pageable());
        return ResponseEntity.ok(backgroundService.findByFilter(
                new BackgroundFilter(
                        StringUtils.hasText(filter.name()) ? filter.name() : null,
                        StringUtils.hasText(filter.description()) ? filter.description() : null
                ),
                Optional.ofNullable(filter.pageable())
                        .orElse(PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name")))));
    }
}
