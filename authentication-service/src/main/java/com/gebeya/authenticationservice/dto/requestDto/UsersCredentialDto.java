package com.gebeya.authenticationservice.dto.requestDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersCredentialDto {
    private String userName;
    private String password;
}
