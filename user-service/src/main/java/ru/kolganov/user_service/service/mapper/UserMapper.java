package ru.kolganov.user_service.service.mapper;

import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;
import ru.kolganov.user_service.entity.UserEntity;
import ru.kolganov.user_service.exception.InvalidParameterException;
import ru.kolganov.user_service.rest.dto.UserDtoRq;
import ru.kolganov.user_service.rest.dto.UserDtoRs;
import ru.kolganov.user_service.service.model.IdentityProvider;
import ru.kolganov.user_service.service.model.UserModelRq;
import ru.kolganov.user_service.service.model.UserModelRs;

import java.util.List;
import java.util.Optional;

@UtilityClass
public class UserMapper {
    public UserModelRq toUserModelRq(@NonNull final UserDtoRq userDtoRq) {
        return new UserModelRq(
                Optional.ofNullable(userDtoRq.externalId())
                        .orElseThrow(() -> new InvalidParameterException("externalId", "User externalId cannot be null")),
                IdentityProvider.getByName(userDtoRq.provider())
        );
    }

    public UserDtoRs toUserDtoRs(@NonNull final UserModelRs userModelRs) {
        return new UserDtoRs(
                userModelRs.userId(),
                userModelRs.roles()
        );
    }

    public UserModelRs toUserModelRs(@NonNull final UserEntity userEntity) {
        return new UserModelRs(
                userEntity.getId(),
                List.of()
        );
    }
}
