package com.gebeya.authenticationservice.dto.responseDto;

import com.gebeya.authenticationservice.enums.Authority;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationResponseDto {

    private final Authority role;
    private final Integer roleId;
}
