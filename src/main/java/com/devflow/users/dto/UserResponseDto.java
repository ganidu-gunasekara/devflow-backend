package com.devflow.users.dto;

import com.devflow.users.User;
import com.devflow.users.enums.UserType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String name;
    private UserType type;
    private Long companyId;
    private String companyName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserResponseDto from(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setType(user.getType());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        if (user.getCompany() != null) {
            dto.setCompanyId(user.getCompany().getId());
            dto.setCompanyName(user.getCompany().getCompanyName());
        }
        return dto;
    }
}
