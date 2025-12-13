package ru.kolganov.user_service.rest.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestController;
import ru.kolganov.user_service.exception.InvalidParameterException;
import ru.kolganov.user_service.rest.UserApi;
import ru.kolganov.user_service.rest.dto.UserDtoRq;
import ru.kolganov.user_service.rest.dto.UserDtoRs;
import ru.kolganov.user_service.service.UserService;
import ru.kolganov.user_service.service.mapper.UserMapper;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {
    private final UserService userService;

    @Override
    public ResponseEntity<UserDtoRs> getUserByExternalId(@Nullable final UserDtoRq userDtoRq) {
        return Optional.ofNullable(userDtoRq)
                .map(m -> {
                    log.info("Received user request: externalId='{}', provider='{}'", m.externalId(), m.provider());
                    return ResponseEntity.ok(
                            UserMapper.toUserDtoRs(
                                    userService.getOrCreateUserByExternalIdAndProvider(UserMapper.toUserModelRq(userDtoRq))));
                })
                .orElseThrow(() -> new InvalidParameterException("userDtoRq", "User request body cannot be null"));
    }
}
