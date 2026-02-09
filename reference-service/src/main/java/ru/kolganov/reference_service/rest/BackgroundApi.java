package ru.kolganov.reference_service.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kolganov.reference_service.rest.dto.background.BackgroundCreateRqDto;
import ru.kolganov.reference_service.rest.dto.background.BackgroundRsDto;
import ru.kolganov.reference_service.rest.dto.background.BackgroundFilterRqDto;
import ru.kolganov.reference_service.rest.dto.PageDtoRs;
import ru.kolganov.reference_service.rest.dto.background.BackgroundUpdateRqDto;

@RequestMapping("/rest/api/v1/background")
public interface BackgroundApi {

    @GetMapping("/{code}")
    ResponseEntity<BackgroundRsDto> getByCode(@PathVariable final String code);

    @PostMapping("/search")
    ResponseEntity<PageDtoRs<BackgroundRsDto>> findByFilter(@RequestBody final BackgroundFilterRqDto filter);

    @PostMapping()
    ResponseEntity<BackgroundRsDto> create(@RequestBody final BackgroundCreateRqDto backgroundCreateRqDto);

    @PatchMapping("/{code}")
    ResponseEntity<BackgroundRsDto> update(@PathVariable final String code, @RequestBody final BackgroundUpdateRqDto backgroundUpdateRqDto);

    @DeleteMapping("/{code}")
    ResponseEntity<Void> delete(@PathVariable final String code);
}
