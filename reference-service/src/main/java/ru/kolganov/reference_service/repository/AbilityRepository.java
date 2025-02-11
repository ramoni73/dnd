package ru.kolganov.reference_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolganov.reference_service.entity.Ability;

import java.util.UUID;

public interface AbilityRepository extends JpaRepository<Ability, UUID> {
}
