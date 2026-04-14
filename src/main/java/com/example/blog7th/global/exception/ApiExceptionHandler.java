package com.example.blog7th.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    // @Valid 유효성 검사 실패 시, 400 Bad Request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // 첫 번째 에러 메시지
        String errorMessage = ex.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        errors.put("error", "Bad Request");
        errors.put("message", errorMessage);

        return ResponseEntity.badRequest().body(errors);
    }

    // 서비스 로직 에러 처리 ,403 Forbidden, 404 Not Found
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> errors = new HashMap<>();
        String errorCode = ex.getMessage(); // 서비스에서 throw new RuntimeException("POST4041") 한 값
        if ("COMMON403".equals(errorCode)) {
            errors.put("error", "Forbidden");
            errors.put("message", "해당 작업을 수행할 권한이 없습니다.");
            return ResponseEntity.status(403).body(errors);
        } else if ("POST4041".equals(errorCode)) {
            errors.put("error", "Not Found");
            errors.put("message", "존재하지 않는 게시글입니다.");
            return ResponseEntity.status(404).body(errors);
        }

        // 정의되지 않은 기타 에러
        errors.put("error", "Internal Server Error");
        errors.put("message", errorCode);
        return ResponseEntity.status(500).body(errors);
    }
}