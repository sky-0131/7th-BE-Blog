package com.example.blog7th.auth.controller;

import com.example.blog7th.auth.dto.AuthRequest;
import com.example.blog7th.auth.service.AuthService;
import com.example.blog7th.auth.dto.TokenResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입 경로
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody AuthRequest request) {
        authService.signUp(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    // 로그인 경로
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody AuthRequest request, HttpServletResponse response) {
        TokenResponse tokens = authService.login(request);
        ResponseCookie cookie = ResponseCookie.from("refreshToken", tokens.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(14 * 24 * 60 * 60)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok(new TokenResponse(tokens.getAccessToken()));
    }

    // refresh 토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(
            @CookieValue(value = "refreshToken") String refreshToken) {

        // 1. 서비스의 메서드가 String을 반환한다면, 이를 TokenResponse로 감싸야 합니다.
        String newAccessToken = authService.refreshAccessToken(refreshToken);
        TokenResponse tokens = new TokenResponse(newAccessToken);

        return ResponseEntity.ok(tokens);
    }
}
