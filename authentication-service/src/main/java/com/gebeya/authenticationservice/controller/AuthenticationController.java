package com.gebeya.authenticationservice.controller;

import com.gebeya.authenticationservice.dto.AuthResponse;
import com.gebeya.authenticationservice.dto.SignInRequest;
import com.gebeya.authenticationservice.dto.SignUpRequest;
import com.gebeya.authenticationservice.dto.requestDto.ValidationRequestDto;
import com.gebeya.authenticationservice.dto.responseDto.AuthenticationResponseDto;
import com.gebeya.authenticationservice.dto.responseDto.ValidationResponseDto;
import com.gebeya.authenticationservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/signIn")
    public ResponseEntity<AuthenticationResponseDto> signIn(@RequestBody SignInRequest signInRequest) {
        return authenticationService.signIn(signInRequest);
    }
    @PostMapping("/validate")
    public ResponseEntity<ValidationResponseDto> validate(@RequestBody ValidationRequestDto request)
    {
        return authenticationService.validate(request);
    }
}
