package com.example.blog7th.user.dto;

import com.example.blog7th.user.domain.User;
import com.example.blog7th.user.domain.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "사용자 정보 응답 DTO")
public class UserResponse {

    @Schema(description = "사용자 고유 ID", example = "1")
    private final Long userId;

    @Schema(description = "사용자 이메일", example = "dev_lion@example.com")
    private final String email;

    @Schema(description = "사용자 닉네임", example = "sky0131")
    private final String nickname;

    @Schema(description = "사용자 권한", example = "USER")
    private final UserRole role;

    public UserResponse(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.role = user.getRole();
    }
}
