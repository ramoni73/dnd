package ru.kolganov.reference_service.rest.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import ru.kolganov.reference_service.exception.InvalidParameterException;
import ru.kolganov.reference_service.rest.RaceApi;
import ru.kolganov.reference_service.rest.dto.PageDtoRs;
import ru.kolganov.reference_service.rest.dto.RaceDto;
import ru.kolganov.reference_service.rest.dto.RaceFilterRqDto;
import ru.kolganov.reference_service.service.RaceService;
import ru.kolganov.reference_service.service.filter.CharacterClassFilter;
import ru.kolganov.reference_service.service.filter.RaceFilter;
import ru.kolganov.reference_service.service.mapper.CharacterClassMapper;
import ru.kolganov.reference_service.service.mapper.PageMapper;
import ru.kolganov.reference_service.service.mapper.RaceMapper;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RaceController implements RaceApi {
    private final RaceService raceService;

    @Override
    public ResponseEntity<RaceDto> findByCode(String code) {
        return Optional.ofNullable(code)
                .map(c -> {
                    log.info("Fetching race by code: {}", c);
                    return ResponseEntity.ok(RaceMapper.toDto(raceService.getByCode(c)));
                })
                .orElseThrow(() -> new InvalidParameterException("code", "Race code cannot be null"));
    }

    @Override
    public ResponseEntity<PageDtoRs<RaceDto>> findByFilter(RaceFilterRqDto filter) {
        return Optional.ofNullable(filter)
                .map(m -> {
                    log.info("Received race filter request: name={}, description={}, pageable={}", m.name(), m.description(), m.pageDto());
                    return ResponseEntity.ok(
                            PageMapper.toPageDtoRs(raceService.findByFilter(
                                            new RaceFilter(
                                                    StringUtils.hasText(m.name()) ? m.name() : null,
                                                    StringUtils.hasText(m.description()) ? m.description() : null
                                            ),
                                            PageMapper.toPageable(m.pageDto()))
                                    .map(RaceMapper::toDto)));
                })
                .orElseThrow(() -> new InvalidParameterException("filter", "Race filter cannot be null"));
    }
}
