package com.gebeya.authenticationservice.dto;

import com.gebeya.authenticationservice.enums.Authority;
import lombok.Data;

@Data
public class SignUpRequest {
    private String userName;
    private String password;
    private Authority authority;
    private Integer roleId;
}
