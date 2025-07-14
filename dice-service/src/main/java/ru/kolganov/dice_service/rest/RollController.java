package ru.kolganov.dice_service.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kolganov.dice_service.model.DiceModelRq;
import ru.kolganov.dice_service.model.DiceModelRs;
import ru.kolganov.dice_service.rest.dto.DiceDto;
import ru.kolganov.dice_service.rest.dto.RollDtoRq;
import ru.kolganov.dice_service.rest.dto.RollDtoRs;
import ru.kolganov.dice_service.service.DiceRollService;
import ru.kolganov.dice_service.service.RollService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class RollController {
    private final DiceRollService diceRollService;

    @PostMapping("rest/api/v1/roll")
    public ResponseEntity<RollDtoRs> getRoll(final @RequestBody RollDtoRq rollDtoRq) {
        if (Objects.nonNull(rollDtoRq)) {
            final DiceModelRs result = diceRollService.getRolls(
                    rollDtoRq.diceRolls().stream()
                            .map(m -> new DiceModelRq(m.diceType(), m.count(), m.rollType()))
                            .toList());

            return ResponseEntity.ok(new RollDtoRs(
                    result.result(),
                    result.diceResults().stream()
                            .map(m -> new DiceDto(m.diceType(), m.rollResult()))
                            .toList()));
        }

        return ResponseEntity.badRequest().body(new RollDtoRs("The body of request must not be empty"));
    }
}
