package ru.kolganov.character_service.model.weapons;

import org.springframework.data.util.Pair;

public record WeaponModel(
        String name,
        Integer attackBonus,
        Pair<String, String> damage,
        String notes
) {
}
