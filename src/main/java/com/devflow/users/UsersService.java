package com.devflow.users;

import com.devflow.company.Company;
import com.devflow.company.CompanyRepository;
import com.devflow.exception.AppException;
import com.devflow.projects.Project;
import com.devflow.projects.ProjectsRepository;
import com.devflow.users.dto.CreateUserDto;
import com.devflow.users.dto.UpdateUserDto;
import com.devflow.users.dto.UserResponseDto;
import com.devflow.users.dto.UserSummaryDto;
import com.devflow.utils.HashUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final CompanyRepository companyRepository;
    private final ProjectsRepository projectsRepository;
    public UserResponseDto create(CreateUserDto dto) {
        if (usersRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new org.springframework.dao.DuplicateKeyException("Email already exists");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setPassword(HashUtil.sha256(dto.getPassword()));
        user.setType(dto.getType());

        Long companyId = dto.getCompany_id();
        if (companyId != null && companyId != 0){
            Company company = companyRepository.findById(companyId).orElseThrow(() -> new AppException.NotFoundException("Company Not Found"));
            user.setCompany(company);
        }
        return UserResponseDto.from(usersRepository.save(user));
    }

    public Page<UserSummaryDto> findAll(UserFilterRequest filters) {
        Pageable pageable = PageRequest.of(filters.page,filters.size);
        return usersRepository.findAllUsers(filters.isShowDeleted(),filters.getKeyword(), filters.getCompany_id(),pageable)
                .map(UserSummaryDto::from);
    }

    public UserResponseDto findOne(Long id) {
        return usersRepository.findUserById(id).map(UserResponseDto::from).orElseThrow(()->new AppException.NotFoundException("User not found"));
    }

    public Optional<User> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Transactional
    public UserResponseDto update(Long id, UpdateUserDto dto) {
        User user = usersRepository.findUserById(id)
                .orElseThrow(() -> new AppException.NotFoundException("User not found"));

        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getPassword() != null) user.setPassword(HashUtil.sha256(dto.getPassword()));
        if (dto.getType() != null) user.setType(dto.getType());
        if (dto.getRefreshToken() != null) user.setRefreshToken(dto.getRefreshToken());
        if (dto.getIsDeleted() != null) user.setDeleted(dto.getIsDeleted());
        if (dto.getCompanyId() != null) {
            Company company = companyRepository.findById(dto.getCompanyId())
                    .orElseThrow(() -> new AppException.NotFoundException("Company not found"));
            user.setCompany(company);
        }
        if (dto.getSelectedProjectId() != null) {
            Project project = projectsRepository.findById(dto.getSelectedProjectId())
                    .orElseThrow(() -> new AppException.NotFoundException("Project not found"));
            user.setSelectedProject(project);
        }

        return UserResponseDto.from(usersRepository.save(user));
    }

    public void updateRefreshToken(Long id, String rawRefreshToken) {
        User user = usersRepository.findById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("User not found"));
        user.setRefreshToken(HashUtil.sha256(rawRefreshToken));
        usersRepository.save(user);
    }

    public void clearRefreshToken(Long id) {
        User user = usersRepository.findById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("User not found"));
        user.setRefreshToken(null);
        usersRepository.save(user);
    }

    public boolean remove(Long id) {
        User user = usersRepository.findById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("User with ID " + id + " not found"));
        user.setDeleted(true);
        usersRepository.save(user);
        return true;
    }
}
