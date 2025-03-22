package ru.kolganov.character_service.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.kolganov.character_service.model.CharacterModel;

public interface CharacterApi {

    @GetMapping("/rest/api/v1/character/{characterId}")
    ResponseEntity<CharacterModel> findByCharacterId(@Nullable @PathVariable final String characterId);

    @PostMapping("/rest/api/v1/character")
    ResponseEntity<CharacterModel> saveCharacter(@Nullable @RequestBody final CharacterModel character);

    @DeleteMapping("/rest/api/v1/character/{characterId}")
    ResponseEntity<Boolean> deleteCharacter(@Nullable @PathVariable final String characterId);
}
