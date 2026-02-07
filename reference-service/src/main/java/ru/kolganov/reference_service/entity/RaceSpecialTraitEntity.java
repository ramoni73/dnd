package ru.kolganov.reference_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Cache;

import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "race_special_trait")
@NoArgsConstructor
@AllArgsConstructor
public class RaceSpecialTraitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "value", nullable = false, length = 500)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id", nullable = false)
    private RaceEntity raceEntity;
}
