package ru.kolganov.character_service.service;

import org.springframework.lang.NonNull;
import ru.kolganov.character_service.model.CharacterModel;

public interface CharacterService {
    CharacterModel getCharacterById(@NonNull String id);

    CharacterModel saveCharacter(@NonNull CharacterModel character);

    void deleteCharacterById(@NonNull String id);
}
