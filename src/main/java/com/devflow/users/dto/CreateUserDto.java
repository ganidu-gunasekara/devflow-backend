package com.devflow.users.dto;

import com.devflow.company.Company;
import com.devflow.users.enums.UserType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @NotNull
    private UserType type;

    @Nullable
    private Long company_id;
}
