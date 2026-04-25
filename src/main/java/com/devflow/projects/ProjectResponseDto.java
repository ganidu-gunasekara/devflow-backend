package com.devflow.projects;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProjectResponseDto {
    private Long id;
    private String name;
    private String description;
    private Long companyId;
    private String companyName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Long> user_ids;

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

    public static ProjectResponseDto fromWithUsers(Project project) {
        ProjectResponseDto dto = from(project);
        dto.setUser_ids(project.getUserProjects().stream()
                        .map(up -> up.getUser().getId())
                        .collect(Collectors.toList())
        );
        return dto;
    }
}
