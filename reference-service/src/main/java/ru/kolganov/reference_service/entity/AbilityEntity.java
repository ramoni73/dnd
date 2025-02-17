package ru.kolganov.reference_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import ru.kolganov.reference_service.entity.enums.AbilityCode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "ability")
@NoArgsConstructor
@AllArgsConstructor
public class AbilityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NaturalId
    @Column(name = "code", unique = true, nullable = false, length = 3)
    @Enumerated(EnumType.STRING)
    private AbilityCode code;

    @Column(name = "name", unique = true, nullable = false, length = 20)
    private String name;

    @Column(name = "description", nullable = false, length = 150)
    private String description;

    @OneToMany(mappedBy = "abilityEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SkillEntity> skillEntities = new ArrayList<>();
}
