package com.devflow.company;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/create")
    public ResponseEntity<CompanyResponseDto> create(@Valid @RequestBody CompanyDto dto) {
        return ResponseEntity.ok(companyService.create(dto));
    }

    @GetMapping("/get")
    public ResponseEntity<List<CompanyResponseDto>> findAll() {
        return ResponseEntity.ok(companyService.findAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CompanyResponseDto> findOne(@PathVariable Long id) {
        return companyService.findOne(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> remove(@PathVariable Long id) {
        companyService.remove(id);
        return ResponseEntity.ok(Map.of("deleted", true, "message", "Company deleted successfully"));
    }
}
