package com.example.blog7th.auth.kakao.dto;

import com.example.blog7th.user.domain.User;
import com.example.blog7th.user.domain.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
public class OAuthAttributes {
    private final String providerId;
    private final String nickname;
    private final String email;
    private final String profileImage;

    @Builder
    public OAuthAttributes(String providerId, String nickname, String email, String profileImage) {
        this.providerId = providerId;
        this.nickname = nickname;
        this.email = email;
        this.profileImage = profileImage;
    }

    //카카오에서 제공하는 Map 형태의 유저 정보를 파싱하여 OAuthAttributes 객체로 변환.
    @SuppressWarnings("unchecked")
    public static OAuthAttributes ofKakao(Map<String, Object> attributes) {
        // 카카오 고유 ID 추출
        String providerId = String.valueOf(attributes.get("id"));

        // properties 안, 닉네임, 프로필 이미지 추출
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        String nickname = (properties != null) ? (String) properties.get("nickname") : "KakaoUser";
        String profileImage = (properties != null) ? (String) properties.get("profile_image") : null;

        // kakao_account 안, 이메일 추출 (선택 동의 항목 예외 처리)
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String email = null;
        if (kakaoAccount != null && kakaoAccount.get("email") != null) {
            email = (String) kakaoAccount.get("email");
        } else {
            email = providerId + "@kakao.com";
        }

        return OAuthAttributes.builder()
                .providerId(providerId)
                .nickname(nickname)
                .email(email)
                .profileImage(profileImage)
                .build();
    }

    //OAuthAttributes 값을 토대로 최초 가입할 User 엔티티 객체를 생성.

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(UUID.randomUUID().toString())
                .nickname(nickname)
                .profileImage(profileImage)
                .role(UserRole.USER)
                .provider("KAKAO")
                .providerId(providerId)
                .build();
    }
}
