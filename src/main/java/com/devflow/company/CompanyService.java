package com.devflow.company;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyResponseDto create(CompanyDto dto) {
        if (companyRepository.findByCompanyName(dto.getCompanyName()).isPresent()) {
            throw new DuplicateKeyException("Company already exists");
        }

        Company company = new Company();
        company.setCompanyName(dto.getCompanyName());
        return CompanyResponseDto.from(companyRepository.save(company));
    }

    public List<CompanyResponseDto> findAll() {
        return companyRepository.findAll().stream().map(CompanyResponseDto::from).collect(Collectors.toList());
    }

    public Optional<CompanyResponseDto> findOne(Long id) {
        return companyRepository.findById(id)
                .map(CompanyResponseDto::from);
    }

    public CompanyResponseDto update(Long id, CompanyDto dto) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));
        company.setCompanyName(dto.getCompanyName());
        return CompanyResponseDto.from(companyRepository.save(company));
    }

    public boolean remove(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Company with ID " + id + " not found"));
        company.setDeleted(true);
        companyRepository.save(company);
        return true;
    }
}
