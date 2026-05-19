package com.example.blog7th.auth.kakao.controller;

import com.example.blog7th.auth.dto.TokenResponse;
import com.example.blog7th.auth.kakao.service.KakaoService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/kakao")
public class KakaoController {

    private final KakaoService kakaoService;

    // 카카오 로그인 완료 후, 리다이렉트 되는 주소
    @GetMapping("/callback")
    public ResponseEntity<TokenResponse> kakaoCallback(@RequestParam String code, HttpServletResponse response) {
        // 사용자 정보 조회 및 로그인/회원가입 처리
        TokenResponse tokens = kakaoService.kakaoLogin(code);

        // 리프레시 토큰 쿠키 생성
        ResponseCookie cookie = ResponseCookie.from("refreshToken", tokens.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(14 * 24 * 60 * 60) // 14일
                .sameSite("Strict") // CSRF 방지
                .build();
        // 응답 헤더에 쿠키 추가
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // 엑세스 토큰만 전달
        return ResponseEntity.ok(new TokenResponse(tokens.getAccessToken()));
    }
}
