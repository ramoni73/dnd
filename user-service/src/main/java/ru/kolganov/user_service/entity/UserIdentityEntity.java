package ru.kolganov.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kolganov.user_service.model.IdentityProvider;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "user_identity", indexes = {
        @Index(name = "idx_uid_external_provider", columnList = "external_id, provider", unique = true),
        @Index(name = "idx_uid_user_provider", columnList = "user_id, provider", unique = true)
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserIdentityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private IdentityProvider provider;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}
