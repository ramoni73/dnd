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
@Table(name = "race")
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Immutable
public class RaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NaturalId
    @Column(name = "code", unique = true, nullable = false, length = 50)
    private String code;

    @Column(name = "name", unique = true, nullable = false, length = 50)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "creature_type", nullable = false)
    private String creatureType;

    @Column(name = "creature_size", nullable = false)
    private String creatureSize;

    @Column(name = "speed", nullable = false)
    private Integer speed;

    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @OneToMany(mappedBy = "raceEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubRaceEntity> subRaceEntities = new ArrayList<>();

    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @OneToMany(mappedBy = "raceEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RaceSpecialTraitEntity> specialTraits = new ArrayList<>();
}
