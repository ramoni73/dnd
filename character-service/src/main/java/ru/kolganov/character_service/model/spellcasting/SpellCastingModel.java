package ru.kolganov.character_service.model.spellcasting;

import java.util.List;

public record SpellCastingModel(
        String spellCastingAbility,
        Integer spellSaveDc,
        Integer spellAttackBonus,
        List<SpellSlotsModel> spellSlots,
        List<SpellModel> spells
) {
}
