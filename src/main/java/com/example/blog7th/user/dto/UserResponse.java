package com.example.blog7th.user.dto;

import com.example.blog7th.user.domain.User;
import com.example.blog7th.user.domain.UserRole;
import lombok.Getter;

@Getter
public class UserResponse {
    private final Long userId;
    private final String email;
    private final String nickname;
    private final UserRole role;

    public UserResponse(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.role = user.getRole();
    }
}
