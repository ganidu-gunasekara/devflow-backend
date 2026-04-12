package com.devflow.projects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectDto {

    @NotBlank
    private String projectName;

    @NotNull
    private Long companyId;
}
