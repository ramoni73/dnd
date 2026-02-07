package ru.kolganov.reference_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.kolganov.reference_service.entity.CharacterClassEntity;
import ru.kolganov.reference_service.exception.ElementNotFoundException;
import ru.kolganov.reference_service.repository.CharacterClassRepository;
import ru.kolganov.reference_service.repository.CharacterClassSpecifications;
import ru.kolganov.reference_service.service.CharacterClassService;
import ru.kolganov.reference_service.service.filter.CharacterClassFilter;
import ru.kolganov.reference_service.service.mapper.CharacterClassMapper;
import ru.kolganov.reference_service.service.model.CharacterClassModel;

@Slf4j
@Service
@RequiredArgsConstructor
public class CharacterClassServiceImpl implements CharacterClassService {
    private final CharacterClassRepository characterClassRepository;

    @Override
    @Cacheable(value = "classes", key = "#code")
    public CharacterClassModel getByCode(@NonNull final String code) {
        return characterClassRepository.findByCode(code)
                .map(CharacterClassMapper::toModel)
                .orElseThrow(() -> new ElementNotFoundException(code, "Class not found: %s".formatted(code)));
    }

    @Override
    public Page<CharacterClassModel> findByFilter(@NonNull final CharacterClassFilter filter, @NonNull final Pageable pageable) {
        Specification<CharacterClassEntity> specification = CharacterClassSpecifications.withFilter(filter);
        return characterClassRepository.findAll(specification, pageable).map(CharacterClassMapper::toModel);
    }
}
