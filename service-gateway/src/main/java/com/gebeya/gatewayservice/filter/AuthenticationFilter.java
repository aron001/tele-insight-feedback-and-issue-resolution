package com.gebeya.gatewayservice.filter;

import com.gebeya.gatewayservice.dto.TokenDTO;
import com.gebeya.gatewayservice.dto.ValidationResponseDTO;
import com.gebeya.gatewayservice.exception.HeaderNotFoundException;
import com.gebeya.gatewayservice.exception.UnAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouteValidator validator;
    private final WebClient.Builder webClientBuilder;
    public AuthenticationFilter(RouteValidator validator, WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.validator = validator;
        this.webClientBuilder = webClientBuilder;
    }
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                // Header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new HeaderNotFoundException("Missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                TokenDTO token = TokenDTO.builder().token(authHeader).build();
                return validateAuthorization(token)
                        .flatMap(response -> {
                            ServerHttpRequest mutatedHttpRequest = exchange.getRequest().mutate()
                                    .header("Role", response.getRole().toString())
                                    .header("RoleId",response.getRoleId().toString())
                                    .build();
                            ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedHttpRequest).build();
                            return chain.filter(mutatedExchange);
                        })
                        .onErrorResume(error -> {
                            // Handle validation error
                            return chain.filter(exchange);
                        });
            }
            return chain.filter(exchange);
        });
    }
    public Mono<ValidationResponseDTO> validateAuthorization(TokenDTO token) {
        return webClientBuilder.build()
                .post()
                .uri("http://authentication-service/api/v1/auth/validate")
                .header("Content-Type", "application/json")
                .body(Mono.just(token), TokenDTO.class)
                .retrieve()
                .toEntity(ValidationResponseDTO.class)
                .map(ResponseEntity::getBody)
                .onErrorResume(error -> {
                    // Handle errors gracefully, e.g., log and return a user-friendly message
//                    log.error("Error occurred during validation:", error);
                    return Mono.error(new UnAuthorizedException("UnAuthorized"));
                });
    }
    public static class Config{

}
}


