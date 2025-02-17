package ru.kolganov.reference_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.util.*;

@Data
@Entity
@Builder
@Table(name = "background")
@NoArgsConstructor
@AllArgsConstructor
public class BackgroundEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NaturalId
    @Column(unique = true, nullable = false, length = 50)
    private String code;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "background_ability",
            joinColumns = @JoinColumn(name = "background_id"),
            inverseJoinColumns = @JoinColumn(name = "ability_id")
    )
    private Set<AbilityEntity> abilities = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "background_feat",
            joinColumns = @JoinColumn(name = "background_id"),
            inverseJoinColumns = @JoinColumn(name = "feat_id")
    )
    private Set<FeatEntity> featEntities = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "background_skill",
            joinColumns = @JoinColumn(name = "background_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<SkillEntity> skillEntities = new HashSet<>();

    @OneToMany(mappedBy = "backgroundEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BackgroundInstrumentEntity> instruments = new ArrayList<>();

    @OneToMany(mappedBy = "backgroundEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BackgroundEquipmentEntity> equipment = new ArrayList<>();
}
