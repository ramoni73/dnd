package ru.kolganov.gateway_service.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class UserIdHeaderFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(auth -> auth != null && auth.isAuthenticated())
                .map(auth -> {
                    Object principal = auth.getPrincipal();
                    if (principal instanceof OAuth2User) {
                        OAuth2User user = (OAuth2User) principal;
                        String userId = (String) user.getAttribute("userId");
                        @SuppressWarnings("unchecked")
                        List<String> roles = (List<String>) user.getAttribute("roles");

                        if (userId != null) {
                            ServerHttpRequest.Builder request = exchange.getRequest().mutate();
                            request.header("X-User-Id", userId);
                            if (roles != null) {
                                request.header("X-User-Roles", String.join(",", roles));
                            }
                            return exchange.mutate().request(request.build()).build();
                        }
                    }
                    return exchange;
                })
                .defaultIfEmpty(exchange)
                .flatMap(chain::filter);
    }

    @Override
    public int getOrder() {
        return -1; // до маршрутизации
    }
}
