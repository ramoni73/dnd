package ru.kolganov.reference_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
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
import ru.kolganov.reference_service.event.model.application.BackgroundCreatedApplicationEvent;
import ru.kolganov.reference_service.event.model.application.BackgroundDeletedApplicationEvent;
import ru.kolganov.reference_service.event.model.application.BackgroundUpdatedApplicationEvent;
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

import java.util.Optional;
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
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Cacheable(value = "backgrounds", key = "#code")
    public BackgroundModel getByCode(@NonNull final String code) {
        return BackgroundMapper.toModel(getBackgroundEntityByCode(code));
    }

    @Override
    public Page<BackgroundModel> findByFilter(@NonNull final BackgroundFilter backgroundFilter, @NonNull final Pageable pageable) {
        Specification<BackgroundEntity> specification = BackgroundSpecifications.withFilter(backgroundFilter);
        return backgroundRepository.findAll(specification, pageable).map(BackgroundMapper::toModel);
    }

    @Override
    @Transactional
    public BackgroundModel create(@NonNull final BackgroundModel backgroundModel) {
        Optional<BackgroundEntity> existing = backgroundRepository.findByCodeOrName(
                backgroundModel.code(),
                backgroundModel.name()
        );

        if (existing.isPresent()) {
            BackgroundEntity found = existing.get();
            if (found.getCode().equals(backgroundModel.code())) {
                throw new ElementAlreadyExistsException(
                        backgroundModel.code(),
                        "Background with code '%s' already exists".formatted(backgroundModel.code())
                );
            } else {
                throw new ElementAlreadyExistsException(
                        backgroundModel.name(),
                        "Background with name '%s' already exists".formatted(backgroundModel.name())
                );
            }
        }

        FeatEntity featEntity = getFeatEntityByCode(backgroundModel.feat().code());

        BackgroundModel created = BackgroundMapper.toModel(backgroundRepository.save(
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

        eventPublisher.publishEvent(new BackgroundCreatedApplicationEvent(this, created));

        return created;
    }

    @Override
    @CacheEvict(value = {"backgrounds", "backgrounds:search"}, allEntries = true)
    @Transactional
    public BackgroundModel update(@NonNull final BackgroundModel backgroundModel) {
        BackgroundEntity entity = getBackgroundEntityByCode(backgroundModel.code());

        if (backgroundModel.name() != null && !backgroundModel.name().equals(entity.getName())) {
            if (backgroundRepository.existsByName(backgroundModel.name())) {
                throw new ElementAlreadyExistsException(
                        backgroundModel.name(),
                        "Background with name '%s' already exists".formatted(backgroundModel.name())
                );
            }
            entity.setName(backgroundModel.name());
        }

        BackgroundModel oldModel = BackgroundMapper.toModel(entity);

        if (backgroundModel.description() != null) {
            entity.setDescription(backgroundModel.description());
        }
        if (backgroundModel.equipment() != null) {
            entity.setEquipment(backgroundModel.equipment());
        }
        if (backgroundModel.instruments() != null) {
            entity.setInstruments(backgroundModel.instruments());
        }

        if (backgroundModel.feat() != null) {
            FeatEntity feat = getFeatEntityByCode(backgroundModel.feat().code());
            entity.setFeatEntity(feat);
        }

        if (backgroundModel.abilities() != null) {
            if (backgroundModel.abilities().isEmpty()) {
                entity.getAbilities().clear();
            } else {
                Set<AbilityEntity> abilities = resolveAbilities(backgroundModel.abilities());
                entity.getAbilities().clear();
                entity.getAbilities().addAll(abilities);
            }
        }

        if (backgroundModel.skills() != null) {
            if (backgroundModel.skills().isEmpty()) {
                entity.getSkillEntities().clear();
            } else {
                Set<SkillEntity> skills = resolveSkills(backgroundModel.skills());
                entity.getSkillEntities().clear();
                entity.getSkillEntities().addAll(skills);
            }
        }

        BackgroundModel newModel = BackgroundMapper.toModel(backgroundRepository.save(entity));

        eventPublisher.publishEvent(new BackgroundUpdatedApplicationEvent(this, oldModel, newModel));

        return newModel;
    }

    @Override
    @CacheEvict(value = "backgrounds", key = "#code")
    @Transactional
    public void delete(@NonNull final String code) {
        backgroundRepository.delete(getBackgroundEntityByCode(code));
        eventPublisher.publishEvent(new BackgroundDeletedApplicationEvent(this, code));
    }

    private BackgroundEntity getBackgroundEntityByCode(String code) {
        return backgroundRepository.findByCode(code)
                .orElseThrow(() -> new ElementNotFoundException(code, "Background not found: %s".formatted(code)));
    }

    private FeatEntity getFeatEntityByCode(String code) {
        return featRepository.findByCode(code)
                .orElseThrow(() -> new ElementNotFoundException(code, "Feat not found: %s".formatted(code)));
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
