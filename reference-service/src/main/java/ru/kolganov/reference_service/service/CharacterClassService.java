package ru.kolganov.reference_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import ru.kolganov.reference_service.service.filter.CharacterClassFilter;
import ru.kolganov.reference_service.service.model.CharacterClassModel;

public interface CharacterClassService {
    CharacterClassModel getByCode(@NonNull String code);
    Page<CharacterClassModel> findByFilter(@NonNull CharacterClassFilter filter, @NonNull Pageable pageable);
}
