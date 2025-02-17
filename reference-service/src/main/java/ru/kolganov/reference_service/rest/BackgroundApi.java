package ru.kolganov.reference_service.rest;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import ru.kolganov.reference_service.model.BackgroundModel;
import ru.kolganov.reference_service.service.filter.BackgroundFilter;

public interface BackgroundApi {

    @GetMapping("/rest/api/v1/background/{code}")
    ResponseEntity<BackgroundModel> getByCode(@PathVariable final String code);

    @PostMapping("rest/api/v1/background")
    ResponseEntity<Page<BackgroundModel>> findByFilter(@NonNull @RequestBody final BackgroundFilter filter);
}
