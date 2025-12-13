package ru.kolganov.user_service.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kolganov.user_service.exception.ElementNotFoundException;
import ru.kolganov.user_service.repository.UserRepository;
import ru.kolganov.user_service.service.UserService;
import ru.kolganov.user_service.service.mapper.UserMapper;
import ru.kolganov.user_service.service.model.UserModelRq;
import ru.kolganov.user_service.service.model.UserModelRs;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserModelRs getOrCreateUserByExternalIdAndProvider(@NonNull final UserModelRq userModelRq) {
        return userRepository.findUserByProviderAndExternalId(userModelRq.provider(), userModelRq.externalId())
                .map(UserMapper::toUserModelRs)
                .orElseThrow(() -> new ElementNotFoundException(
                        userModelRq.provider() + "|" + userModelRq.externalId(),
                        "User not found: %s".formatted(userModelRq.provider() + "|" + userModelRq.externalId())));
    }
}
