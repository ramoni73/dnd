package ru.kolganov.character_service.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.kolganov.character_service.model.CharacterModel;
import ru.kolganov.character_service.service.CharacterService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CharacterController implements CharacterApi {
    private final CharacterService characterService;

    @Override
    public ResponseEntity<CharacterModel> findByCharacterId(String characterId) {
        return ResponseEntity.ok(characterService.getCharacterById(characterId));
    }

    @Override
    public ResponseEntity<CharacterModel> createCharacter(CharacterModel character) {
        return ResponseEntity.ok(characterService.createCharacter(character));
    }

    @Override
    public ResponseEntity<CharacterModel> updateCharacter(String characterId, CharacterModel character) {
        return null;
    }

    @Override
    public ResponseEntity<Boolean> deleteCharacter(String characterId) {
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
