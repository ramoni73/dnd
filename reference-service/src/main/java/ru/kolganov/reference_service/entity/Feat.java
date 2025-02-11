package ru.kolganov.reference_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import ru.kolganov.reference_service.entity.enums.FeatCategory;

import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "feat")
@NoArgsConstructor
@AllArgsConstructor
public class Feat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NaturalId
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private FeatCategory category;
}
