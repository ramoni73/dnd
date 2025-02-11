package ru.kolganov.reference_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "background_equipment")
@NoArgsConstructor
@AllArgsConstructor
public class BackgroundEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "background_id", nullable = false)
    private Background background;

    @Column(nullable = false)
    private String value;
}
