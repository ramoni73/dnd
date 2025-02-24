package ru.kolganov.reference_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import ru.kolganov.reference_service.service.model.BackgroundModel;
import ru.kolganov.reference_service.service.filter.BackgroundFilter;

public interface BackgroundService {
    BackgroundModel getByCode(@NonNull String code);
    Page<BackgroundModel> findByFilter(@NonNull BackgroundFilter backgroundFilter, @NonNull Pageable pageable);
}
