package com.devflow.projects;

import com.devflow.company.Company;
import com.devflow.company.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectsService {

    private final ProjectsRepository projectsRepository;
    private final CompanyRepository companyRepository;

    public ProjectResponseDto create(ProjectDto dto) {
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));

        Project project = new Project();
        project.setName(dto.getProjectName());
        project.setCompany(company);
        return ProjectResponseDto.from(projectsRepository.save(project));
    }

    public List<ProjectResponseDto> findAll() {
        return projectsRepository.findAllByIsDeleted(0)
                .stream()
                .map(ProjectResponseDto::from)
                .collect(Collectors.toList());
    }

    public Optional<ProjectResponseDto> findOne(Long id) {
        return projectsRepository.findById(id)
                .map(ProjectResponseDto::from);
    }

    public ProjectResponseDto update(Long id, ProjectDto dto) {
        Project project = projectsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project with ID " + id + " not found"));

        if (dto.getProjectName() != null) project.setName(dto.getProjectName());
        if (dto.getCompanyId() != null) {
            Company company = companyRepository.findById(dto.getCompanyId())
                    .orElseThrow(() -> new EntityNotFoundException("Company not found"));
            project.setCompany(company);
        }

        return ProjectResponseDto.from(projectsRepository.save(project));
    }

    public boolean remove(Long id) {
        Project project = projectsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project with ID " + id + " not found"));
        project.setIsDeleted(1);
        projectsRepository.save(project);
        return true;
    }
}
