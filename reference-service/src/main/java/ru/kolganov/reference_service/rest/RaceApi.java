package ru.kolganov.reference_service.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kolganov.reference_service.rest.dto.PageDtoRs;
import ru.kolganov.reference_service.rest.dto.RaceDto;
import ru.kolganov.reference_service.rest.dto.RaceFilterRqDto;

@RequestMapping("/rest/api/v1/race")
public interface RaceApi {
    @GetMapping("/{code}")
    ResponseEntity<RaceDto> findByCode(@PathVariable final String code);

    @PostMapping()
    ResponseEntity<PageDtoRs<RaceDto>> findByFilter(@RequestBody final RaceFilterRqDto filter);
}
