package ru.kolganov.character_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolganov.character_service.entity.Skill;

import java.util.UUID;

public interface SkillRepository extends JpaRepository<Skill, UUID> {
}
