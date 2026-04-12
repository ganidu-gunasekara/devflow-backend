package com.devflow.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @Value("${app.env:development}")
    private String appEnv;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginDto dto,
                                                     HttpServletResponse response) {
        Map<String, Object> tokens = authService.login(dto);

        Cookie cookie = new Cookie("refresh_token", (String) tokens.get("refreshToken"));
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(appEnv));
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);

        return ResponseEntity.ok(Map.of(
                "user", tokens.get("user"),
                "accessToken", tokens.get("accessToken")
        ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(HttpServletRequest request,
                                                       HttpServletResponse response) {
        String refreshToken = null;

        if (request.getCookies() != null) {
            refreshToken = Arrays.stream(request.getCookies())
                    .filter(c -> "refresh_token".equals(c.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }

        if (refreshToken == null) {
            return ResponseEntity.status(401).build();
        }

        Map<String, String> tokens = authService.refreshTokens(refreshToken);

        Cookie cookie = new Cookie("refresh_token", tokens.get("refreshToken"));
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(appEnv));
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);

        return ResponseEntity.ok(Map.of("accessToken", tokens.get("accessToken")));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@AuthenticationPrincipal String email,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response) {
        // Extract userId from Bearer token
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            Long userId = jwtUtil.extractUserId(authHeader.substring(7));
            authService.logout(userId);
        }

        // Clear cookie
        Cookie cookie = new Cookie("refresh_token", "");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }
}
