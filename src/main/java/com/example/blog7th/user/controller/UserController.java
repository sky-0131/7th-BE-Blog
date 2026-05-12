package com.example.blog7th.user.controller;

import com.example.blog7th.user.dto.UserRequest;
import com.example.blog7th.user.dto.UserResponse;
import com.example.blog7th.user.service.UserService;
import com.example.blog7th.user.repository.UserRepository; // 1. 레포지토리 임포트 추가
import com.example.blog7th.user.domain.User;       // 2. 유저 엔티티 임포트 추가
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional; // 3. 트랜잭션 추가
import org.springframework.web.bind.annotation.*;

@Tag(name = "User API", description = "사용자 정보 조회 및 회원가입 관련 API")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository; // 4. 레포지토리 주입받기

    @Operation(summary = "사용자 상세 조회", description = "특정 사용자의 정보를 조회합니다.")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
        UserResponse response = userService.getUser(userId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "내 정보 조회", description = "현재 로그인한 사용자의 정보를 조회합니다.")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyInfo(@AuthenticationPrincipal User user) {
        // 세션/토큰에서 인증된 유저 정보를 가져와 조회
        UserResponse response = userService.getUser(user.getId());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "회원 가입", description = "새로운 사용자를 등록합니다.")
    @ApiResponse(responseCode = "200", description = "가입 성공 (생성된 유저 ID 반환)")
    @PostMapping
    public ResponseEntity<Long> createUser(@Valid @RequestBody UserRequest request) {
        // 리포지토리에 직접 접근하지 않고 서비스를 통해 처리 (계층 분리)
        Long userId = userService.signUp(request);
        return ResponseEntity.ok(userId);
    }
}
