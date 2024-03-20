package com.gebeya.authenticationservice.service;

import com.gebeya.authenticationservice.dto.AuthResponse;
import com.gebeya.authenticationservice.dto.SignInRequest;
import com.gebeya.authenticationservice.dto.SignUpRequest;
import com.gebeya.authenticationservice.dto.requestDto.ValidationRequestDto;
import com.gebeya.authenticationservice.dto.responseDto.AuthenticationResponseDto;
import com.gebeya.authenticationservice.dto.responseDto.ValidationResponseDto;
import com.gebeya.authenticationservice.model.Users;
import com.gebeya.authenticationservice.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final UsersService userService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse signUp(SignUpRequest signUpRequest) {
        Users user = Users.builder()
                .userName(signUpRequest.getUserName())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .authority(signUpRequest.getAuthority())
                .roleId(signUpRequest.getRoleId())
                .is_active(true)
                .build();
        userService.createUser(user);
        String jwt = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwt).authority(user.getAuthority())
                .build();
    }

    public ResponseEntity<AuthenticationResponseDto> signIn(SignInRequest signInRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequest.getUserName(), signInRequest.getPassword()));
        } catch (AuthenticationException e) {
            throw new RuntimeException(e.getMessage());
        }

        Users user = userRepository.findFirstByUserName(signInRequest.getUserName())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid user name or password"));

        String jwt = jwtService.generateToken(user);
        AuthenticationResponseDto response = AuthenticationResponseDto.builder()
                .token(jwt)
                .authority(user.getAuthority())
                .build();

        return ResponseEntity.ok(response);
    }
    public ResponseEntity<ValidationResponseDto> validate(ValidationRequestDto validationRequest)
    {
        final String userName;
        userName = jwtService.extractUserName(validationRequest.getToken());
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        if (StringUtils.isNotEmpty(userName)) {
            Users users = userService.loadUserByUsername(userName);
            if (jwtService.isTokenValid(validationRequest.getToken(), users)) {
                ValidationResponseDto response = ValidationResponseDto.builder()
                        .role(users.getAuthority())
                        .roleId(users.getRoleId())
                        .build();

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
