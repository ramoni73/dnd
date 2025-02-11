package ru.kolganov.reference_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolganov.reference_service.entity.Skill;

import java.util.UUID;

public interface SkillRepository extends JpaRepository<Skill, UUID> {
}
