package ru.kolganov.reference_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolganov.reference_service.entity.Race;

import java.util.UUID;

public interface RaceRepository extends JpaRepository<Race, UUID> {
}
