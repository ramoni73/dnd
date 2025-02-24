package ru.kolganov.reference_service.rest.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import ru.kolganov.reference_service.exception.InvalidParameterException;
import ru.kolganov.reference_service.rest.CharacterClassApi;
import ru.kolganov.reference_service.rest.dto.CharacterClassDto;
import ru.kolganov.reference_service.rest.dto.CharacterClassFilterRqDto;
import ru.kolganov.reference_service.rest.dto.PageDtoRs;
import ru.kolganov.reference_service.service.CharacterClassService;
import ru.kolganov.reference_service.service.filter.CharacterClassFilter;
import ru.kolganov.reference_service.service.mapper.CharacterClassMapper;
import ru.kolganov.reference_service.service.mapper.PageMapper;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CharacterClassController implements CharacterClassApi {
    private final CharacterClassService characterClassService;

    @Override
    public ResponseEntity<CharacterClassDto> findByCode(@Nullable final String code) {
        return Optional.ofNullable(code)
                .map(c -> {
                    log.info("Fetching class by code: {}", c);
                    return ResponseEntity.ok(CharacterClassMapper.toDto(characterClassService.getByCode(c)));
                })
                .orElseThrow(() -> new InvalidParameterException("code", "Class code cannot be null"));
    }

    @Override
    public ResponseEntity<PageDtoRs<CharacterClassDto>> findByFilter(@Valid @Nullable final CharacterClassFilterRqDto filter) {
        return Optional.ofNullable(filter)
                        .map(m -> {
                            log.info("Received class filter request: name={}, description={}, pageable={}", m.name(), m.description(), m.pageDto());
                            return ResponseEntity.ok(
                                    PageMapper.toPageDtoRs(characterClassService.findByFilter(
                                            new CharacterClassFilter(
                                                    StringUtils.hasText(m.name()) ? m.name() : null,
                                                    StringUtils.hasText(m.description()) ? m.description() : null
                                            ),
                                            PageMapper.toPageable(m.pageDto()))
                                    .map(CharacterClassMapper::toDto)));
                        })
                .orElseThrow(() -> new InvalidParameterException("filter", "Class filter cannot be null"));
    }
}
