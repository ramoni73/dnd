package ru.kolganov.reference_service.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kolganov.reference_service.rest.dto.PageDtoRs;
import ru.kolganov.reference_service.rest.dto.RaceDto;
import ru.kolganov.reference_service.rest.dto.RaceFilterRqDto;

public interface RaceApi {
    @GetMapping("/rest/api/v1/race/{code}")
    ResponseEntity<RaceDto> findByCode(@Nullable @PathVariable final String code);

    @PostMapping("rest/api/v1/race")
    ResponseEntity<PageDtoRs<RaceDto>> findByFilter(@Nullable @RequestBody final RaceFilterRqDto filter);
}
