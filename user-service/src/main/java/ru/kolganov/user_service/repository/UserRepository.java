package ru.kolganov.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kolganov.user_service.entity.UserEntity;
import ru.kolganov.user_service.entity.UserIdentityEntity;
import ru.kolganov.user_service.service.model.IdentityProvider;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query("""
            SELECT u FROM UserEntity u
            JOIN FETCH u.identities uie
            WHERE uie.provider = :provider AND uie.externalId = :externalId
            """)
    Optional<UserEntity> findUserByProviderAndExternalId(
            @Param("provider") IdentityProvider provider,
            @Param("externalId") String externalId
    );
}
