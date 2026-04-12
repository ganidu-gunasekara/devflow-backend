package com.devflow.company;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CompanyResponseDto {
    private Long id;
    private String companyName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CompanyResponseDto from(Company company) {
        CompanyResponseDto dto = new CompanyResponseDto();
        dto.setId(company.getId());
        dto.setCompanyName(company.getCompanyName());
        dto.setCreatedAt(company.getCreatedAt());
        dto.setUpdatedAt(company.getUpdatedAt());
        return dto;
    }
}
