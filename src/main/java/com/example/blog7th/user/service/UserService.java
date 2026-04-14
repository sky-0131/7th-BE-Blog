package com.example.blog7th.user.service;

import com.example.blog7th.user.domain.User;
import com.example.blog7th.user.dto.UserResponse;
import com.example.blog7th.user.repository.UserRepository;
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
}
