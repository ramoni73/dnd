package ru.kolganov.user_service.service;

import org.springframework.lang.NonNull;
import ru.kolganov.user_service.service.model.UserModelRq;
import ru.kolganov.user_service.service.model.UserModelRs;

public interface UserService {

    UserModelRs getOrCreateUserByExternalIdAndProvider(@NonNull UserModelRq userModelRq);
}
