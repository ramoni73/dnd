package ru.kolganov.reference_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.kolganov.reference_service.entity.RaceEntity;
import ru.kolganov.reference_service.exception.ElementNotFoundException;
import ru.kolganov.reference_service.repository.RaceRepository;
import ru.kolganov.reference_service.repository.RaceSpecifications;
import ru.kolganov.reference_service.service.RaceService;
import ru.kolganov.reference_service.service.filter.RaceFilter;
import ru.kolganov.reference_service.service.mapper.RaceMapper;
import ru.kolganov.reference_service.service.model.RaceModel;

@Slf4j
@Service
@RequiredArgsConstructor
public class RaceServiceImpl implements RaceService {
    private final RaceRepository raceRepository;

    @Override
    @Cacheable(value = "race", key = "#code")
    public RaceModel getByCode(@NonNull final String code) {
        return raceRepository.findByCode(code)
                .map(RaceMapper::toModel)
                .orElseThrow(() -> new ElementNotFoundException(code, "Race not found: %s".formatted(code)));
    }

    @Override
    public Page<RaceModel> findByFilter(@NonNull final RaceFilter filter, @NonNull final Pageable pageable) {
        Specification<RaceEntity> specification = RaceSpecifications.withFilter(filter);
        return raceRepository.findAll(specification, pageable).map(RaceMapper::toModel);
    }
}
