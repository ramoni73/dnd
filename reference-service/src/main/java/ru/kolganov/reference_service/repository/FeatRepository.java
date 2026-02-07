package ru.kolganov.reference_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolganov.reference_service.entity.FeatEntity;

import java.util.Set;
import java.util.UUID;

public interface FeatRepository extends JpaRepository<FeatEntity, UUID> {
    Set<FeatEntity> findAllByCodeIn(Set<String> codes);
}
