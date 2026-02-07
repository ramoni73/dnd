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
@Table(name = "sub_race_property")
@NoArgsConstructor
@AllArgsConstructor
public class SubRacePropertyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "value", nullable = false, length = 500)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_race_id", nullable = false)
    private SubRaceEntity subRaceEntity;
}
