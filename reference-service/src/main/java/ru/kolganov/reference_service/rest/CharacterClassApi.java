package ru.kolganov.reference_service.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kolganov.reference_service.rest.dto.CharacterClassDto;
import ru.kolganov.reference_service.rest.dto.CharacterClassFilterRqDto;
import ru.kolganov.reference_service.rest.dto.PageDtoRs;

public interface CharacterClassApi {

    @GetMapping("/rest/api/v1/class/{code}")
    ResponseEntity<CharacterClassDto> findByCode(@Nullable @PathVariable final String code);

    @PostMapping("rest/api/v1/class")
    ResponseEntity<PageDtoRs<CharacterClassDto>> findByFilter(@Nullable @RequestBody final CharacterClassFilterRqDto filter);
}
