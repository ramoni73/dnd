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
    public ResponseEntity<CharacterModel> saveCharacter(CharacterModel character) {
        return ResponseEntity.ok(characterService.saveCharacter(character));
    }

    @Override
    public ResponseEntity<Boolean> deleteCharacter(String characterId) {
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
