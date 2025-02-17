package ru.kolganov.reference_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolganov.reference_service.entity.AbilityEntity;

import java.util.UUID;

public interface AbilityRepository extends JpaRepository<AbilityEntity, UUID> {
}
