package ru.kolganov.character_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolganov.character_service.entity.Feat;

import java.util.UUID;

public interface FeatRepository extends JpaRepository<Feat, UUID> {
}
