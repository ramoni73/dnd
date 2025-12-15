package ru.kolganov.gateway_service.security;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RefreshTokenStore {
    //todo нужен редис
    private final Map<String, String> tokenStore = new ConcurrentHashMap<>();
    private final Map<String, Instant> expiryStore = new ConcurrentHashMap<>();

    public void save(String userId, String refreshToken) {
        tokenStore.put(userId, refreshToken);
        expiryStore.put(userId, Instant.now().plus(Duration.ofDays(7)));
    }

    public boolean isValid(String userId, String refreshToken) {
        String storedToken = tokenStore.get(userId);
        Instant expiry = expiryStore.get(userId);
        return storedToken != null
                && storedToken.equals(refreshToken)
                && expiry != null
                && expiry.isAfter(Instant.now());
    }

    public void invalidate(String userId) {
        tokenStore.remove(userId);
        expiryStore.remove(userId);
    }
}
