package com.devflow.auth;

import com.devflow.users.dto.UserResponseDto;
import lombok.Data;

@Data
public class AuthResponseDto {
    private String accessToken;
    private String refreshToken;
    private UserResponseDto user;
}
