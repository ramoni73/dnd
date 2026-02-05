package ru.kolganov.reference_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Cache;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "character_class")
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Immutable
public class CharacterClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NaturalId
    @Column(name = "code", unique = true, nullable = false, length = 50)
    private String code;

    @Column(name = "name", unique = true, nullable = false, length = 50)
    private String name;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @OneToMany(mappedBy = "characterClassEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CharacterClassPropertyEntity> properties = new ArrayList<>();
}
