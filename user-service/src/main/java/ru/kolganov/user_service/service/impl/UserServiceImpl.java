package ru.kolganov.user_service.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolganov.user_service.entity.UserEntity;
import ru.kolganov.user_service.entity.UserIdentityEntity;
import ru.kolganov.user_service.event.UserCreatedApplicationEvent;
import ru.kolganov.user_service.repository.UserRepository;
import ru.kolganov.user_service.service.UserService;
import ru.kolganov.user_service.service.mapper.UserMapper;
import ru.kolganov.user_service.service.model.UserModelRq;
import ru.kolganov.user_service.service.model.UserModelRs;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public UserModelRs getOrCreateUserByExternalIdAndProvider(@NonNull final UserModelRq userModelRq) {
        return userRepository.findUserByProviderAndExternalId(userModelRq.provider(), userModelRq.externalId())
                .map(UserMapper::toUserModelRs)
                .orElseGet(() -> createNewUser(userModelRq));
    }

    private UserModelRs createNewUser(final UserModelRq request) {
        log.info("Creating new user for provider={}, externalId={}", request.provider(), request.externalId());

        Instant now = Instant.now();
        UserEntity user = UserEntity.builder()
                .createdAt(now)
                .updatedAt(now)
                .build();

        UserIdentityEntity identity = UserIdentityEntity.builder()
                .user(user)
                .provider(request.provider())
                .externalId(request.externalId())
                .createdAt(now)
                .build();

        user.setIdentities(List.of(identity));

        UserEntity savedUser = userRepository.save(user);

        eventPublisher.publishEvent(new UserCreatedApplicationEvent(
                this,
                savedUser.getId(),
                identity.getProvider(),
                identity.getExternalId()
        ));

        return UserMapper.toUserModelRs(savedUser);
    }
}
