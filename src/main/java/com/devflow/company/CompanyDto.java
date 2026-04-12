package com.devflow.company;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CompanyDto {

    @NotBlank
    private String companyName;
}
