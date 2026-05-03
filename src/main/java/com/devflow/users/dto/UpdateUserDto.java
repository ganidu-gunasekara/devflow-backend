package com.devflow.users.dto;

import com.devflow.users.enums.UserType;
import lombok.Data;

@Data
public class UpdateUserDto {
    private String email;
    private String name;
    private String password;
    private UserType type;
    private String refreshToken;
    private Boolean isDeleted;
    private Long companyId;
    private Long selectedProjectId;
}
