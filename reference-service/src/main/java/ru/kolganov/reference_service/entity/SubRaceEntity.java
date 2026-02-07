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
@Table(name = "sub_race")
@NoArgsConstructor
@AllArgsConstructor
public class SubRaceEntity {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id", nullable = false)
    private RaceEntity raceEntity;

    @OneToMany(mappedBy = "subRaceEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubRacePropertyEntity> properties = new ArrayList<>();
}
