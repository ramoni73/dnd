package ru.kolganov.reference_service.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.kolganov.reference_service.rest.dto.BackgroundDto;
import ru.kolganov.reference_service.rest.dto.BackgroundFilterRqDto;
import ru.kolganov.reference_service.rest.dto.PageDtoRs;

public interface BackgroundApi {

    @GetMapping("/rest/api/v1/background/{code}")
    ResponseEntity<BackgroundDto> getByCode(@Nullable @PathVariable final String code);

    @PostMapping("rest/api/v1/background")
    ResponseEntity<PageDtoRs<BackgroundDto>> findByFilter(@Nullable @RequestBody final BackgroundFilterRqDto filter);
}
