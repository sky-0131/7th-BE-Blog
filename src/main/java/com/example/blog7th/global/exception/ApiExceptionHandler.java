package com.example.blog7th.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    // 1. @Valid 유효성 검사 실패 (400 Bad Request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return createResponse("Bad Request", errorMessage, 400);
    }

    // 2. 게시글 없음 에러 처리 (404 Not Found)
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePostNotFoundException(PostNotFoundException ex) {
        return createResponse("Not Found", ex.getMessage(), 404);
    }

    // 3. 권한 없음 에러 처리 (403 Forbidden)
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String, String>> handleForbiddenException(ForbiddenException ex) {
        return createResponse("Forbidden", ex.getMessage(), 403);
    }

    // 4. 그 외 예측하지 못한 모든 런타임 에러 (500 Internal Server Error)
    // 이제 여기서는 NPE 같은 진짜 시스템 에러만 잡힙니다.
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleAllRuntimeException(RuntimeException ex) {
        return createResponse("Internal Server Error", "서버 내부 오류가 발생했습니다.", 500);
    }

    // 공통 응답 포맷을 만드는 편의 메서드
    private ResponseEntity<Map<String, String>> createResponse(String error, String message, int status) {
        Map<String, String> response = new HashMap<>();
        response.put("error", error);
        response.put("message", message);
        return ResponseEntity.status(status).body(response);
    }
}