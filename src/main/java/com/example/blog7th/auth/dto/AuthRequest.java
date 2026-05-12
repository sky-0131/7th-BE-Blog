package com.example.blog7th.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthRequest {
    private String email;
    private String password;
    private String username; // 또는 nickname (엔티티 필드명에 맞추세요)
}
