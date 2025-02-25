package ru.kolganov.reference_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import ru.kolganov.reference_service.service.filter.RaceFilter;
import ru.kolganov.reference_service.service.model.RaceModel;

public interface RaceService {
    RaceModel getByCode(@NonNull String code);
    Page<RaceModel> findByFilter(@NonNull RaceFilter filter, @NonNull Pageable pageable);
}
