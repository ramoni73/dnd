package ru.kolganov.reference_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.kolganov.reference_service.entity.BackgroundEntity;
import ru.kolganov.reference_service.exception.ElementNotFoundException;
import ru.kolganov.reference_service.service.model.BackgroundModel;
import ru.kolganov.reference_service.repository.BackgroundRepository;
import ru.kolganov.reference_service.repository.BackgroundSpecifications;
import ru.kolganov.reference_service.service.BackgroundService;
import ru.kolganov.reference_service.service.filter.BackgroundFilter;
import ru.kolganov.reference_service.service.mapper.BackgroundMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackgroundServiceImpl implements BackgroundService {
    private final BackgroundRepository backgroundRepository;

    @Override
    public BackgroundModel getByCode(@NonNull final String code) {
        return backgroundRepository.findByCode(code)
                .map(BackgroundMapper::toModel)
                .orElseThrow(() -> new ElementNotFoundException(code, "Background not found: " + code));
    }

    @Override
    public Page<BackgroundModel> findByFilter(@NonNull final BackgroundFilter backgroundFilter, @NonNull final Pageable pageable) {
        Specification<BackgroundEntity> specification = BackgroundSpecifications.withFilter(backgroundFilter);
        return backgroundRepository.findAll(specification, pageable).map(BackgroundMapper::toModel);
    }
}
