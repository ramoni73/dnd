package ru.kolganov.reference_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import ru.kolganov.reference_service.entity.CharacterClassEntity;

import java.util.Optional;
import java.util.UUID;

public interface CharacterClassRepository extends JpaRepository<CharacterClassEntity, UUID>, JpaSpecificationExecutor<CharacterClassEntity> {
    Optional<CharacterClassEntity> findByCode(@NonNull String code);
}
