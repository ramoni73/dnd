package ru.kolganov.gateway_service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RefreshTokenStore {
    private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";
    private static final Duration REFRESH_TOKEN_TTL = Duration.ofDays(7);

    private final ReactiveRedisOperations<String, String> redisOps;

    public Mono<Void> save(final String userId, final String refreshToken) {
        final String key = REFRESH_TOKEN_PREFIX + userId;
        return redisOps.opsForValue()
                .set(key, refreshToken, REFRESH_TOKEN_TTL)
                .then();
    }

    public Mono<Boolean> isValid(final String userId, final String refreshToken) {
        final String key = REFRESH_TOKEN_PREFIX + userId;
        return redisOps.opsForValue()
                .get(key)
                .map(storedToken -> storedToken.equals(refreshToken))
                .defaultIfEmpty(false);
    }

    public Mono<Long> invalidate(final String userId) {
        final String key = REFRESH_TOKEN_PREFIX + userId;
        return redisOps.unlink(key);
    }
}
