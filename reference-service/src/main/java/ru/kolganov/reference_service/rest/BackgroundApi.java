package ru.kolganov.reference_service.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kolganov.reference_service.rest.dto.background.BackgroundRqDto;
import ru.kolganov.reference_service.rest.dto.background.BackgroundRsDto;
import ru.kolganov.reference_service.rest.dto.background.BackgroundFilterRqDto;
import ru.kolganov.reference_service.rest.dto.PageDtoRs;

public interface BackgroundApi {

    @GetMapping("/rest/api/v1/background/{code}")
    ResponseEntity<BackgroundRsDto> getByCode(@PathVariable final String code);

    @PostMapping("rest/api/v1/background/search")
    ResponseEntity<PageDtoRs<BackgroundRsDto>> findByFilter(@RequestBody final BackgroundFilterRqDto filter);

    @PostMapping("rest/api/v1/background")
    ResponseEntity<BackgroundRsDto> create(@RequestBody final BackgroundRqDto backgroundRqDto);

    @DeleteMapping("rest/api/v1/background/{code}")
    ResponseEntity<Void> delete(@PathVariable final String code);
}
