package com.devflow.company;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    private static final Logger log = LoggerFactory.getLogger(CompanyController.class);
    @PostMapping("/create")
    public ResponseEntity<CompanyResponseDto> create(@RequestBody CompanyDto dto) {
        log.info("DTO class: {}", dto.getClass().getName());
        log.info("companyName: '{}'", dto.getCompanyName());
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
