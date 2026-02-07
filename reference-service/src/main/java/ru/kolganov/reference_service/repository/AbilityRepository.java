package ru.kolganov.reference_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kolganov.reference_service.entity.AbilityEntity;
import ru.kolganov.reference_service.entity.enums.AbilityCode;

import java.util.Set;
import java.util.UUID;

public interface AbilityRepository extends JpaRepository<AbilityEntity, UUID> {

    @Query("SELECT a FROM AbilityEntity a WHERE a.code IN :codes")
    Set<AbilityEntity> findAllByCodeIn(@Param("codes") Set<AbilityCode> codes);
}
