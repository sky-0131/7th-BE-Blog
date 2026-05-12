package com.example.blog7th.auth.service;

import com.example.blog7th.auth.dto.TokenResponse;
import com.example.blog7th.auth.dto.AuthRequest;
import com.example.blog7th.global.config.security.JwtTokenProvider;
import com.example.blog7th.user.domain.User;
import com.example.blog7th.user.domain.UserRole;
import com.example.blog7th.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입: 비밀번호 암호화 및 중복 체크
    @Transactional
    public void signUp(AuthRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        User user = User.builder()
                .email(request.getEmail())
                // PasswordEncoder를 통한 암호화 저장
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getUsername())
                .role(UserRole.USER)
                .build();

        userRepository.save(user);
    }

    // 로그인: 신원 확인 및 토큰 발급
    @Transactional
    public TokenResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

        // 암호화된 비밀번호와 입력값 비교
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        // Access/Refresh 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken();

        return new TokenResponse(accessToken, refreshToken);
    }
}
