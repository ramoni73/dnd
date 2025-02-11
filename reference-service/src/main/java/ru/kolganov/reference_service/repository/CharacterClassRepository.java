package ru.kolganov.reference_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolganov.reference_service.entity.CharacterClass;

import java.util.UUID;

public interface CharacterClassRepository extends JpaRepository<CharacterClass, UUID> {
}
