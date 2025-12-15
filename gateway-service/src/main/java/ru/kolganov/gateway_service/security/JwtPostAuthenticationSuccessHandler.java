package ru.kolganov.gateway_service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtPostAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RefreshTokenStore refreshTokenStore;

    @Override
    public Mono<Void> onAuthenticationSuccess(final WebFilterExchange webFilterExchange, final Authentication authentication) {
        final ServerWebExchange exchange = webFilterExchange.getExchange();

        final OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
        final Map<String, Object> attributes = oauth2Token.getPrincipal().getAttributes();

        final String userId = (String) attributes.get("userId");
        final String roles = String.join(",", (Iterable<String>) attributes.get("roles"));

        final String accessJwt = jwtService.generateAccessToken(userId, roles);
        final String refreshJwt = jwtService.generateRefreshToken(userId);

        return refreshTokenStore.save(userId, refreshJwt)
                .then(Mono.defer(() -> {
                    final String jsonResponse = objectMapper.createObjectNode()
                            .put("access_token", accessJwt)
                            .put("refresh_token", refreshJwt)
                            .put("token_type", "Bearer")
                            .toString();

                    final byte[] bytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
                    exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    exchange.getResponse().getHeaders().setContentLength(bytes.length);
                    exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.OK);

                    return exchange.getResponse().writeWith(
                            Flux.just(exchange.getResponse().bufferFactory().wrap(bytes))
                    );
                }));
    }
}
