package ru.kolganov.reference_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolganov.reference_service.entity.Feat;

import java.util.UUID;

public interface FeatRepository extends JpaRepository<Feat, UUID> {
}
