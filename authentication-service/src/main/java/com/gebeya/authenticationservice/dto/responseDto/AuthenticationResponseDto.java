package com.gebeya.authenticationservice.dto.responseDto;

import com.gebeya.authenticationservice.enums.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponseDto {

    private String token;
    private Authority authority;
}
