package com.devflow.auth;

import com.devflow.users.dto.UserResponseDto;
import lombok.Data;

@Data
public class LoginResponseDto {
    private String accessToken;
    private UserResponseDto user;
}