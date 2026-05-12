package com.example.blog7th.user.service;

import com.example.blog7th.user.domain.User;
import com.example.blog7th.user.dto.UserRequest;
import com.example.blog7th.user.dto.UserResponse;
import com.example.blog7th.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    // ID로 유저 한 명 조회 (Controller용)
    public UserResponse getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("USER4041"));
        return new UserResponse(user);
    }

    // ID로 유저 엔티티 조회 (PostService 등 내부 로직용)
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("USER4041"));
    }

    /**
     * 회원 가입
     * @param request 회원가입 정보
     * @return 생성된 유저의 ID
     */
    @Transactional // 데이터 변경이 일어나므로 쓰기 트랜잭션 적용
    public Long signUp(@Valid UserRequest request) {
        // 중복 가입 검증
        validateDuplicateUser(request.getEmail());

        // DTO -> Entity 변환 및 저장
        User user = request.toEntity();
        User savedUser = userRepository.save(user);

        return savedUser.getId();
    }

    // 중복 이메일 검증 로직
    private void validateDuplicateUser(String email) {
        userRepository.findByEmail(email).ifPresent(u -> {
            throw new RuntimeException("이미 가입된 이메일입니다.");
        });
    }
}
