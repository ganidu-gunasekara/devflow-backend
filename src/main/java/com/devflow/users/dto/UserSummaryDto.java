package com.devflow.users.dto;

import com.devflow.users.User;
import com.devflow.users.enums.UserType;
import lombok.Data;

@Data
public class UserSummaryDto {
    private Long id;
    private String email;
    private String name;
    private UserType type;
    private Long companyId;
    private String companyName;

    public static UserSummaryDto from(User user) {
        UserSummaryDto dto = new UserSummaryDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setType(user.getType());
        if (user.getCompany() != null) {
            dto.setCompanyId(user.getCompany().getId());
            dto.setCompanyName(user.getCompany().getCompanyName());
        }
        return dto;
    }
}