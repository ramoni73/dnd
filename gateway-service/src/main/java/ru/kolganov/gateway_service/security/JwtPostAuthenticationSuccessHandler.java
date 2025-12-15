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
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtPostAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> onAuthenticationSuccess(final WebFilterExchange webFilterExchange, final Authentication authentication) {
        final ServerWebExchange exchange = webFilterExchange.getExchange();

        final OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
        final Map<String, Object> attributes = oauth2Token.getPrincipal().getAttributes();

        final String userId = (String) attributes.get("userId");
        final String roles = String.join(",", (Iterable<String>) attributes.get("roles"));

        final String jwt = jwtService.generateToken(userId, roles);

        final String jsonResponse = objectMapper.createObjectNode()
                .put("access_token", jwt)
                .put("token_type", "Bearer")
                .put("expires_in", 15 * 60) // 15 минут в секундах
                .toString();

        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.OK);

        final byte[] responseBytes = jsonResponse.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        exchange.getResponse().getHeaders().setContentLength(responseBytes.length);

        return exchange.getResponse().writeWith(
                reactor.core.publisher.Flux.just(exchange.getResponse().bufferFactory().wrap(responseBytes))
        );
    }
}
