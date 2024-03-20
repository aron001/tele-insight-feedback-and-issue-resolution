package com.gebeya.ticketingservice.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AddAccountRequestDto {
    private String name;
    private String email;
    private String userName;
    private String password;
}
