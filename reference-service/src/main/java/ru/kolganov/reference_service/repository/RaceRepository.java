package ru.kolganov.reference_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import ru.kolganov.reference_service.entity.RaceEntity;

import java.util.Optional;
import java.util.UUID;

public interface RaceRepository extends JpaRepository<RaceEntity, UUID>, JpaSpecificationExecutor<RaceEntity> {
    Optional<RaceEntity> findByCode(@NonNull String code);
}
