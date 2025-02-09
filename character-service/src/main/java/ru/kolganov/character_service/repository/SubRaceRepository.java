package ru.kolganov.character_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolganov.character_service.entity.SubRace;

import java.util.UUID;

public interface SubRaceRepository extends JpaRepository<SubRace, UUID> {
}
