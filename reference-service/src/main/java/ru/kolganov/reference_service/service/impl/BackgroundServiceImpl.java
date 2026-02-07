package ru.kolganov.reference_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolganov.reference_service.entity.AbilityEntity;
import ru.kolganov.reference_service.entity.BackgroundEntity;
import ru.kolganov.reference_service.entity.FeatEntity;
import ru.kolganov.reference_service.entity.SkillEntity;
import ru.kolganov.reference_service.entity.enums.AbilityCode;
import ru.kolganov.reference_service.exception.ElementAlreadyExistsException;
import ru.kolganov.reference_service.exception.ElementNotFoundException;
import ru.kolganov.reference_service.exception.ElementsNotFoundException;
import ru.kolganov.reference_service.repository.*;
import ru.kolganov.reference_service.service.model.AbilityModel;
import ru.kolganov.reference_service.service.model.BackgroundModel;
import ru.kolganov.reference_service.service.BackgroundService;
import ru.kolganov.reference_service.service.filter.BackgroundFilter;
import ru.kolganov.reference_service.service.mapper.BackgroundMapper;
import ru.kolganov.reference_service.service.model.SkillModel;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackgroundServiceImpl implements BackgroundService {
    private final BackgroundRepository backgroundRepository;
    private final AbilityRepository abilityRepository;
    private final FeatRepository featRepository;
    private final SkillRepository skillRepository;

    @Override
    @Cacheable(value = "backgrounds", key = "#code")
    public BackgroundModel getByCode(@NonNull final String code) {
        return backgroundRepository.findByCode(code)
                .map(BackgroundMapper::toModel)
                .orElseThrow(() -> new ElementNotFoundException(code, "Background not found: %s".formatted(code)));
    }

    @Override
    public Page<BackgroundModel> findByFilter(@NonNull final BackgroundFilter backgroundFilter, @NonNull final Pageable pageable) {
        Specification<BackgroundEntity> specification = BackgroundSpecifications.withFilter(backgroundFilter);
        return backgroundRepository.findAll(specification, pageable).map(BackgroundMapper::toModel);
    }

    @Override
    @Transactional
    public BackgroundModel create(@NonNull final BackgroundModel backgroundModel) {
        if (backgroundRepository.existsByCode(backgroundModel.code())) {
            throw new ElementAlreadyExistsException(
                    backgroundModel.code(),
                    "Background with code '%s' already exists".formatted(backgroundModel.code())
            );
        }

        if (backgroundRepository.existsByName(backgroundModel.name())) {
            throw new ElementAlreadyExistsException(
                    backgroundModel.name(),
                    "Background with name '%s' already exists".formatted(backgroundModel.name())
            );
        }

        FeatEntity featEntity = featRepository.findByCode(backgroundModel.feat().code())
                .orElseThrow(() -> new ElementNotFoundException(
                        backgroundModel.feat().code(),
                        "Feat not found: %s".formatted(backgroundModel.feat().code())));

        return BackgroundMapper.toModel(backgroundRepository.save(
                BackgroundEntity.builder()
                        .code(backgroundModel.code())
                        .name(backgroundModel.name())
                        .description(backgroundModel.description())
                        .featEntity(featEntity)
                        .abilities(resolveAbilities(backgroundModel.abilities()))
                        .skillEntities(resolveSkills(backgroundModel.skills()))
                        .equipment(backgroundModel.equipment())
                        .instruments(backgroundModel.instruments())
                        .build()));
    }

    private Set<AbilityEntity> resolveAbilities(Set<AbilityModel> abilities) {
        return resolve(
                abilities,
                m -> AbilityCode.getByName(m.code()),
                abilityRepository::findAllByCodeIn,
                AbilityEntity::getCode,
                "abilities"
        );
    }

    private Set<SkillEntity> resolveSkills(Set<SkillModel> skills) {
        return resolve(
                skills,
                SkillModel::code,
                skillRepository::findAllByCodeIn,
                SkillEntity::getCode,
                "skills"
        );
    }

    private <T, M, C> Set<T> resolve(
            Set<M> models,
            Function<M, C> extractor,
            Function<Set<C>, Set<T>> finder,
            Function<T, C> entityCodeExtractor,
            String entityName) {

        Set<C> codes = models.stream().map(extractor).collect(Collectors.toSet());
        Set<T> entities = finder.apply(codes);

        Set<C> foundCodes = entities.stream()
                .map(entityCodeExtractor)
                .collect(Collectors.toSet());

        Set<C> missingCodes = codes.stream()
                .filter(code -> !foundCodes.contains(code))
                .collect(Collectors.toSet());

        if (!missingCodes.isEmpty()) {
            Set<String> missingCodesStr = missingCodes.stream()
                    .map(String::valueOf)
                    .collect(Collectors.toSet());
            throw new ElementsNotFoundException(
                    missingCodesStr,
                    "%s are not found: %s".formatted(entityName, missingCodesStr));
        }

        return entities;
    }
}
