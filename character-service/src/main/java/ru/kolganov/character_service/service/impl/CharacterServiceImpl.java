package ru.kolganov.character_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.kolganov.character_service.model.CharacterModel;
import ru.kolganov.character_service.repository.CharacterRepository;
import ru.kolganov.character_service.service.CharacterService;

@Slf4j
@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepository characterRepository;

    @Override
    public CharacterModel getCharacterById(@NonNull final String id) {
        return characterRepository.findById(id)
                .orElseThrow();
    }

    @Override
    public CharacterModel saveCharacter(@NonNull final CharacterModel character) {
        return characterRepository.save(character);
    }

    @Override
    public void deleteCharacterById(@NonNull final String id) {
        characterRepository.deleteById(id);
    }
}
