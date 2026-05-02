package com.devflow.projects;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ProjectDto {

    @NotBlank
    private String projectName;

    @Nullable
    private Long companyId;

    private List<Long> users;
}
