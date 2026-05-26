package com.example.blog7th.auth.kakao.service;

import com.example.blog7th.auth.dto.TokenResponse;
import com.example.blog7th.auth.kakao.dto.OAuthAttributes;
import com.example.blog7th.global.config.security.JwtTokenProvider;
import com.example.blog7th.user.domain.User;
import com.example.blog7th.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Transactional
    public TokenResponse kakaoLogin(String code) {
        // 인가 코드로 카카오 액세스 토큰 요청
        String kakaoAccessToken = getKakaoAccessToken(code);

        // 토큰으로 카카오 사용자 정보 조회 (OAuthAttributes 사용)
        OAuthAttributes attributes = getKakaoUserInfo(kakaoAccessToken);

        // DB 조회 후 없으면 자동 회원가입 (attributes.toEntity() 활용)
        User user = userRepository.findByProviderAndProviderId("KAKAO", attributes.getProviderId())
                .orElseGet(() -> userRepository.save(attributes.toEntity()));

        // 서비스 전용 자체 JWT 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken();


        return new TokenResponse(accessToken, refreshToken);
    }

    // 카카오 액세스 토큰 발급 API 통신
    private String getKakaoAccessToken(String code) {
        String tokenUrl = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);

        try {
            return objectMapper.readTree(response.getBody()).get("access_token").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("카카오 토큰 파싱 실패", e);
        }
    }

    //카카오 사용자 정보 조회 API 통신 및 OAuthAttributes 변환
    private OAuthAttributes getKakaoUserInfo(String accessToken) {
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(userInfoUrl, HttpMethod.POST, request, String.class);

        try {
            // JSON 문자열을 계층구조 Map으로 변환한 뒤 DTO 변환 메서드에 전달
            Map<String, Object> attributes = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
            return OAuthAttributes.ofKakao(attributes);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("카카오 유저 정보 파싱 실패", e);
        }
    }
}
