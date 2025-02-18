package ru.kolganov.reference_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import ru.kolganov.reference_service.entity.BackgroundEntity;

import java.util.Optional;
import java.util.UUID;

public interface BackgroundRepository extends JpaRepository<BackgroundEntity, UUID>, JpaSpecificationExecutor<BackgroundEntity> {
    Optional<BackgroundEntity> findByCode(@NonNull String code);
}
