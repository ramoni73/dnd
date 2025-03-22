package ru.kolganov.character_service.model.spellcasting;

public record SpellSlotsModel(
        Integer level,
        Integer total,
        Integer current
) {
}
