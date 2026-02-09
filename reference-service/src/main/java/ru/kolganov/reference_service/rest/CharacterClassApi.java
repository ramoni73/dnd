package ru.kolganov.reference_service.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kolganov.reference_service.rest.dto.CharacterClassDto;
import ru.kolganov.reference_service.rest.dto.CharacterClassFilterRqDto;
import ru.kolganov.reference_service.rest.dto.PageDtoRs;

@RequestMapping("/rest/api/v1/class")
public interface CharacterClassApi {

    @GetMapping("/{code}")
    ResponseEntity<CharacterClassDto> findByCode(@PathVariable final String code);

    @PostMapping()
    ResponseEntity<PageDtoRs<CharacterClassDto>> findByFilter(@RequestBody final CharacterClassFilterRqDto filter);
}
