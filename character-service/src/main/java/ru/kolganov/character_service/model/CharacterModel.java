package ru.kolganov.character_service.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
}
