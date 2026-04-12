package com.devflow.projects;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProjectResponseDto {
    private Long id;
    private String name;
    private String description;
    private Long companyId;
    private String companyName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProjectResponseDto from(Project project) {
        ProjectResponseDto dto = new ProjectResponseDto();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setCreatedAt(project.getCreatedAt());
        dto.setUpdatedAt(project.getUpdatedAt());
        if (project.getCompany() != null) {
            dto.setCompanyId(project.getCompany().getId());
            dto.setCompanyName(project.getCompany().getCompanyName());
        }
        return dto;
    }
}
