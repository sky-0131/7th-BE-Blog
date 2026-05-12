package com.example.blog7th.user.dto;

import com.example.blog7th.user.domain.User;
import com.example.blog7th.user.domain.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "회원 가입 및 정보 수정 요청 DTO")
public class UserRequest {
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Schema(description = "사용자 이메일 (로그인 ID)", example = "dev_lion@example.com")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    @Schema(description = "비밀번호 (8~16자, 특수문자 포함)", example = "password123!")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Schema(description = "사용자 닉네임", example = "sky0131")
    private String nickname;

    @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.png")
    private String profileImage;

    @Schema(description = "사용자 권한 (USER 또는 ADMIN)", example = "USER", allowableValues = {"USER", "ADMIN"})
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
