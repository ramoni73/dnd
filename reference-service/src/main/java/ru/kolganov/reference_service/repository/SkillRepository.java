package ru.kolganov.reference_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolganov.reference_service.entity.SkillEntity;

import java.util.Set;
import java.util.UUID;

public interface SkillRepository extends JpaRepository<SkillEntity, UUID> {
    Set<SkillEntity> findAllByCodeIn(Set<String> codes);
}
