package ru.kolganov.dice_service.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kolganov.dice_service.exception.InvalidParameterException;
import ru.kolganov.dice_service.model.*;
import ru.kolganov.dice_service.rest.dto.*;
import ru.kolganov.dice_service.service.DiceRollService;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
public class RollController {
    private final DiceRollService diceRollService;

    @PostMapping("rest/api/v1/roll")
    public ResponseEntity<RollDtoRs> getRoll(final @RequestBody RollDtoRq rollDtoRq) {
        if (Objects.nonNull(rollDtoRq)) {
            final DiceModelRs result = diceRollService.getRolls(
                    rollDtoRq.diceRolls().stream()
                            .map(m -> new DiceModel(DiceType.getByName(m.diceType()), m.count()))
                            .toList());

            return ResponseEntity.ok(new RollDtoRs(
                    result.result(),
                    result.diceResults().stream()
                            .map(m -> new DiceDto(m.diceType().name(), m.rollResult()))
                            .toList()));
        }

        return ResponseEntity.badRequest().body(new RollDtoRs("The body of request must not be empty"));
    }

    @PostMapping("rest/api/v1/roll/{diceType}/{rollType}")
    public ResponseEntity<RollD20DtoRs> getD20Roll(
            final @RequestBody RollD20DtoRq rollD20DtoRq,
            final @PathVariable String diceType,
            final @PathVariable String rollType
    ) {
        if (rollD20DtoRq == null) {
            throw new InvalidParameterException("Request body", "Request body 'rollD20DtoRq' is null");
        }

        final RollD20ModelRs result = diceRollService.getD20Rolls(
                new RollD20ModelRq(
                        RollType.getByName(rollType),
                        DiceType.getByName(diceType),
                        Optional.ofNullable(rollD20DtoRq.diceRolls())
                                .orElse(Collections.emptyList())
                                .stream()
                                .map(n -> new DiceModel(DiceType.getByName(n.diceType()), n.count()))
                                .toList()
                )
        );

        return ResponseEntity.ok(
                new RollD20DtoRs(
                        result.result(),
                        result.diceResults()
                                .stream()
                                .map(m -> new DiceDto(m.diceType().name(), m.rollResult()))
                                .toList(),
                        result.typedDiceResults()
                                .stream()
                                .map(m -> new DiceDto(m.diceType().name(), m.rollResult()))
                                .toList(),
                        result.rollType().name()
                )
        );
    }
}
