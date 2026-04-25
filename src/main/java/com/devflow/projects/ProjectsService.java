package com.devflow.projects;

import com.devflow.company.Company;
import com.devflow.company.CompanyRepository;
import com.devflow.user_project.UserProjectRepository;
import com.devflow.user_project.UserProjectService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class ProjectsService {

    private final UserProjectService userProjectService;
    private final ProjectsRepository projectsRepository;
    private final CompanyRepository companyRepository;

    private static final Logger log = LoggerFactory.getLogger(ProjectsService.class);

    @Transactional
    public ProjectResponseDto create(ProjectDto dto) {
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));

        Project project = new Project();
        project.setName(dto.getProjectName());
        project.setCompany(company);
        project = projectsRepository.save(project);

        if (!dto.getUsers().isEmpty()){
            userProjectService.assignUsersForProject(dto.getUsers(),project);
        }
        return ProjectResponseDto.from(project);
    }

    public List<ProjectResponseDto> findAll(ProjectFilterRequest filters) {
        return projectsRepository.findProjectsWithCompanies(filters.isShowDeleted(), filters.getKeyword(), filters.getId())
                .stream()
                .map(ProjectResponseDto::from)
                .collect(Collectors.toList());
    }

    public Optional<ProjectResponseDto> findOne(ProjectFilterRequest filters) {
        log.info("Finding project with id: {}", filters.getId());

        Optional<Project> project = projectsRepository.findProject(filters.getId());
        log.info("Project found: {}", project.isPresent());
        return projectsRepository.findProject(filters.getId())
                .stream()
                .findFirst()
                .map(ProjectResponseDto::fromWithUsers);
    }

    @Transactional
    public ProjectResponseDto update(Long id, ProjectDto dto) {
        Project project = projectsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project with ID " + id + " not found"));

        if (dto.getProjectName() != null) project.setName(dto.getProjectName());
        if (dto.getCompanyId() != null) {
            Company company = companyRepository.findById(dto.getCompanyId())
                    .orElseThrow(() -> new EntityNotFoundException("Company not found"));
            project.setCompany(company);
        }

        if (!dto.getUsers().isEmpty()){
            userProjectService.assignUsersForProject(dto.getUsers(),project);
        }
        return ProjectResponseDto.from(projectsRepository.save(project));
    }

    public boolean remove(Long id) {
        Project project = projectsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project with ID " + id + " not found"));
        project.setDeleted(false);
        projectsRepository.save(project);
        return true;
    }
}
