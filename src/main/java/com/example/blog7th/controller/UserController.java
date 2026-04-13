package com.example.blog7th.controller;

import com.example.blog7th.dto.UserRequest;
import com.example.blog7th.dto.UserResponse;
import com.example.blog7th.service.UserService;
import com.example.blog7th.repository.UserRepository; // 1. 레포지토리 임포트 추가
import com.example.blog7th.domain.user.User;       // 2. 유저 엔티티 임포트 추가
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; // 3. 트랜잭션 추가
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository; // 4. 레포지토리 주입받기

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
        UserResponse response = userService.getUser(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Transactional // 5. 데이터를 저장할 때는 꼭 붙여줘야 합니다!
    public ResponseEntity<Long> createUser(@RequestBody UserRequest request) {
        // 6. [핵심] 서비스 메서드 대신 여기서 바로 저장합니다.
        User user = request.toEntity();
        User savedUser = userRepository.save(user);

        return ResponseEntity.ok(savedUser.getId());
    }
}
