package com.example.blog7th.dto;

import com.example.blog7th.domain.user.User;
import com.example.blog7th.domain.user.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequest {
    private String email;
    private String password;
    private String nickname;
    private String profileImage;
    private UserRole role; // USER 또는 ADMIN

    // DTO를 엔티티로 변환하는 편의 메서드
    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .profileImage(profileImage)
                .role(role != null ? role : UserRole.USER) // 기본값 USER
                .build();
    }
}