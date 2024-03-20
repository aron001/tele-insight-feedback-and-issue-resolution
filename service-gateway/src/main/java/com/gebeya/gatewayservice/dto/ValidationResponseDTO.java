package com.gebeya.gatewayservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ValidationResponseDTO {
    private final Authority role;
    private final Integer roleId;
}
