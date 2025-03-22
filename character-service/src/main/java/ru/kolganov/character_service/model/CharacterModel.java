package ru.kolganov.character_service.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.kolganov.character_service.model.character_info.CharacterInfoModel;
import ru.kolganov.character_service.model.main_stats.MainStatsModel;
import ru.kolganov.character_service.model.spellcasting.SpellCastingModel;
import ru.kolganov.character_service.model.weapons.WeaponModel;

import java.util.List;

@Data
@Builder
@Document(collection = "character")
public class CharacterModel {
    @Id
    private String id;
    private CharacterInfoModel characterInfo;
    private LevelModel level;
    private ArmorClassModel armorClass;
    private HealthInfoModel healthInfo;
    private MainStatsModel mainStats;
    private SpellCastingModel spellCasting;
    private List<WeaponModel> weapons;
    private EquipmentTrainingAndProficienciesModel equipmentTrainingAndProficiencies;
    private List<String> classFeatures;
    private List<String> raceTraits;
    private List<String> feats;
    private String backstoryAndPersonality;
    private String languages;
    private String equipment;

}
