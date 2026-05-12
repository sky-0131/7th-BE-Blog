package com.example.blog7th.auth.controller;

import com.example.blog7th.auth.dto.AuthRequest;
import com.example.blog7th.auth.service.AuthService;
import com.example.blog7th.auth.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입 경로
    @PostMapping("/auth")
    public ResponseEntity<String> signUp(@RequestBody AuthRequest request) {
        authService.signUp(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    // 로그인 경로
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody AuthRequest request) {
        TokenResponse tokens = authService.login(request);
        return ResponseEntity.ok(tokens);
    }
}
