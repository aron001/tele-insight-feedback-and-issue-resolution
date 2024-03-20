package com.gebeya.ticketingservice.config;

import com.gebeya.ticketingservice.enums.Authority;
import com.gebeya.ticketingservice.security.RoleHeaderAuthenticationProvider;
import com.gebeya.ticketingservice.security.RoleHeaderAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityConfig {

    protected static final String [] UNAUTHORIZED_MATCHERS = {
            "/api/v1/ticketing/feedback/unauthenticated",
            "api/v1/auth/signIn",
            "api/v1/auth/validate"
    };
    protected static final String [] ADMIN_MATCHERS = {
            "/api/v1/ticketing/admin/**",
            "/api/v1/ticketing/department/**",
            "/api/v1/ticketing/issueType/**",
            "/api/v1/ticketing/location/**",
            "/api/v1/ticketing/service/**",
            "/api/v1/ticketing/severity/**",
            "/api/v1/ticketing/ticketStatus/**",
            "/api/v1/ticketing/workTitle/**",
            "/api/v1/ticketing/workType/**",
            "api/v1/ticketing/feedback/**"
    };
    protected static final String [] CUSTOMER_MATCHERS = {
            "/api/v1/ticketing/customer",
            "/api/v1/ticketing/customer/**",
            "/api/v1/ticketing/feedback/customerFeedback"

    };
    protected static final String [] ENGINEER_MATCHERS = {
            "/api/v1/ticketing/engineer",
            "/api/v1/ticketing/engineer/**",
            "/api/v1/ticketing/note/**",
            "/api/v1/ticketing/ticket/**"
    };
    protected static final String [] MANAGER_MATCHERS = {
            "/api/v1/ticketing/engineer/**",
            "/api/v1/ticketing/ticket/**",
            "/api/v1/ticketing/admin/getAllEngineers",
            "/api/v1/ticketing/admin/getEngineerById/**",
            "/api/v1/ticketing/admin/addEngineer",
            "/api/v1/ticketing/admin/updateEngineer"
    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers(UNAUTHORIZED_MATCHERS).permitAll())
                .authorizeHttpRequests(request -> request.requestMatchers(ADMIN_MATCHERS).hasAuthority(String.valueOf(Authority.ADMIN)))
                .authorizeHttpRequests(request -> request.requestMatchers(CUSTOMER_MATCHERS).hasAuthority(String.valueOf(Authority.CUSTOMER)))
                .authorizeHttpRequests(request -> request.requestMatchers(ENGINEER_MATCHERS).hasAuthority(String.valueOf(Authority.ENGINEER)))
                .authorizeHttpRequests(request -> request.requestMatchers(MANAGER_MATCHERS).hasAuthority(String.valueOf(Authority.MANAGER)))
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .exceptionHandling(handling -> {
                    handling.authenticationEntryPoint(unauthorizedEntryPoint());
                    handling.accessDeniedHandler(accessDeniedHandler());

                })
                .addFilterBefore(new RoleHeaderAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new RoleHeaderAuthenticationProvider();
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) ->
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) ->
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
