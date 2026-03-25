package com.example.blog7th.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        errors.put("error", "Bad Request");
        errors.put("message", errorMessage);

        return ResponseEntity.badRequest().body(errors);
    }
}

