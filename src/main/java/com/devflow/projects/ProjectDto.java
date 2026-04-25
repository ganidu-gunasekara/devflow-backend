package com.devflow.projects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ProjectDto {

    @NotBlank
    private String projectName;

    @NotNull
    private Long companyId;

    private List<Long> users;
}
