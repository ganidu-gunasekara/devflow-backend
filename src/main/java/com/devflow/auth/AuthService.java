package com.devflow.auth;

import com.devflow.exception.InvalidCredentialsException;
import com.devflow.users.User;
import com.devflow.users.UsersRepository;
import com.devflow.users.UsersService;
import com.devflow.utils.HashUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepository usersRepository;
    private final UsersService usersService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public Map<String, Object> login(LoginDto dto) {
        User user = usersRepository.findByEmail(dto.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if (user.isDeleted()) {
            throw new InvalidCredentialsException();
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        log.info("User logged in: {}", user.getEmail());

        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getEmail());

        usersService.updateRefreshToken(user.getId(), refreshToken);

        return Map.of(
                "user", Map.of(
                        "id", user.getId(),
                        "email", user.getEmail(),
                        "name", user.getName(),
                        "type", user.getType()
                ),
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }

    public Map<String, String> refreshTokens(String refreshToken) {
        Long userId;
        try {
            userId = jwtUtil.extractUserIdFromRefresh(refreshToken);
        } catch (Exception e) {
            throw new InvalidCredentialsException("Access Denied");
        }

        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new InvalidCredentialsException("Access Denied"));

        if (user.getRefreshToken() == null) {
            throw new InvalidCredentialsException("Access Denied");
        }


        if (!HashUtil.sha256(refreshToken).equals(user.getRefreshToken())) {
            throw new InvalidCredentialsException("Access Denied");
        }

        String newAccessToken = jwtUtil.generateAccessToken(user.getId(), user.getEmail());
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getEmail());

        usersService.updateRefreshToken(user.getId(), newRefreshToken);

        return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
    }

    public Map<String, String> logout(Long userId) {
        usersService.clearRefreshToken(userId);
        return Map.of("message", "Logged out successfully");
    }
}