package ru.kolganov.reference_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolganov.reference_service.entity.Background;

import java.util.UUID;

public interface BackgroundRepository extends JpaRepository<Background, UUID> {
}
