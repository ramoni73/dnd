package ru.kolganov.reference_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolganov.reference_service.entity.SubRace;

import java.util.UUID;

public interface SubRaceRepository extends JpaRepository<SubRace, UUID> {
}
