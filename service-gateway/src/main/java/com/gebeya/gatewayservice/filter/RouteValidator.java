package com.gebeya.gatewayservice.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/api/v1/auth/signIn",
            "/api/v1/auth/validate",
            "/eureka",
            "/v3/api-docs",
            "/swagger-ui/index.html#/**",
            "/api/v1/blog/get",
            "/api/v1/blog/getAll/{id}"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
