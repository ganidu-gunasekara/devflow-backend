package com.devflow.users;

import com.devflow.users.dto.CreateUserDto;
import com.devflow.users.dto.UpdateUserDto;
import com.devflow.users.dto.UserResponseDto;
import com.devflow.users.dto.UserSummaryDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody CreateUserDto dto) {
        return ResponseEntity.ok(usersService.create(dto));
    }

    @GetMapping("/get")
    public ResponseEntity<Page<UserSummaryDto>> findAll(UserFilterRequest filters) {
        return ResponseEntity.ok(usersService.findAll(filters));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserResponseDto> findOne(@PathVariable Long id) {
        log.info("Get user by id");
        return ResponseEntity.ok(usersService.findOne(id));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody UpdateUserDto dto) {
        return ResponseEntity.ok(usersService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> remove(@PathVariable Long id) {
        usersService.remove(id);
        return ResponseEntity.ok(Map.of("deleted", true, "message", "User deleted successfully"));
    }
}
