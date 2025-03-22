package ru.kolganov.character_service.model.main_stats;

import java.util.List;

/**
 * Характеристики
 *
 * @param code        - натуральный идентификатор
 * @param name        - наименование
 * @param score       - значение
 * @param modifier    - модификатор
 * @param savingThrow - спас-бросок
 * @param skills      - список характеристик
 */
public record AbilityModel(
        String code,
        String name,
        Integer score,
        Integer modifier,
        Boolean savingThrow,
        List<SkillModel> skills
) {
}
