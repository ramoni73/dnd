package ru.kolganov.character_service.model.spellcasting;

import org.springframework.data.util.Pair;

public record SpellModel(
        Integer level,
        String name,
        Pair<Integer, String> castingTime,
        Pair<Integer, String> range,
        Boolean concentration,
        Boolean ritual,
        Boolean requiredMaterial,
        String notes
) {
}
