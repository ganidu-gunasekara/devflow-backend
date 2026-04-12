package com.devflow.projects;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectsController {

    private final ProjectsService projectsService;

    @PostMapping("/create")
    public ResponseEntity<ProjectResponseDto> create(@Valid @RequestBody ProjectDto dto) {
        return ResponseEntity.ok(projectsService.create(dto));
    }

    @GetMapping("/get")
    public ResponseEntity<List<ProjectResponseDto>> findAll() {
        return ResponseEntity.ok(projectsService.findAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProjectResponseDto> findOne(@PathVariable Long id) {
        return projectsService.findOne(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ProjectResponseDto> update(@PathVariable Long id, @Valid @RequestBody ProjectDto dto) {
        return ResponseEntity.ok(projectsService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> remove(@PathVariable Long id) {
        projectsService.remove(id);
        return ResponseEntity.ok(Map.of("deleted", true, "message", "Project deleted successfully"));
    }
}
