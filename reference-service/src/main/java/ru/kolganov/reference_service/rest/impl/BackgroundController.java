package ru.kolganov.reference_service.rest.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import ru.kolganov.reference_service.exception.InvalidParameterException;
import ru.kolganov.reference_service.rest.dto.background.BackgroundRqDto;
import ru.kolganov.reference_service.rest.dto.background.BackgroundRsDto;
import ru.kolganov.reference_service.rest.dto.background.BackgroundFilterRqDto;
import ru.kolganov.reference_service.rest.dto.PageDtoRs;
import ru.kolganov.reference_service.service.mapper.BackgroundMapper;
import ru.kolganov.reference_service.service.mapper.PageMapper;
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
    public ResponseEntity<BackgroundRsDto> getByCode(final String code) {
        return Optional.ofNullable(code)
                .map(c -> {
                    log.info("Fetching background by code: '{}'", c);
                    return ResponseEntity.ok(BackgroundMapper.toDto(backgroundService.getByCode(c)));
                })
                .orElseThrow(() -> new InvalidParameterException("code", "Background code cannot be null"));
    }

    @Override
    public ResponseEntity<PageDtoRs<BackgroundRsDto>> findByFilter(@Valid final BackgroundFilterRqDto filter) {
        return Optional.ofNullable(filter)
                .map(m -> {
                    log.info("Received background filter request: name='{}', description='{}', pageable='{}'", m.name(), m.description(), m.pageDto());
                    return ResponseEntity.ok(
                            PageMapper.toPageDtoRs(backgroundService.findByFilter(
                                            new BackgroundFilter(
                                                    StringUtils.hasText(m.name()) ? m.name() : null,
                                                    StringUtils.hasText(m.description()) ? m.description() : null
                                            ),
                                            PageMapper.toPageable(m.pageDto()))
                                    .map(BackgroundMapper::toDto)));
                })
                .orElseThrow(() -> new InvalidParameterException("filter", "Background filter cannot be null"));
    }

    @Override
    public ResponseEntity<BackgroundRsDto> create(@Valid final BackgroundRqDto backgroundRqDto) {
        return Optional.ofNullable(backgroundRqDto)
                .map(m -> {
                    log.info("Received backgroundDto from request: code='{}', name='{}'", m.code(), m.name());
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(BackgroundMapper.toDto(backgroundService.create(BackgroundMapper.toModel(m))));
                })
                .orElseThrow(() -> new InvalidParameterException("backgroundDto", "Background dto cannot be null"));
    }
}
