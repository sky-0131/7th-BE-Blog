package com.example.blog7th.health.controller;

import com.example.blog7th.health.dto.StringRequest;
import com.example.blog7th.health.dto.StringResponse;
import com.example.blog7th.health.service.StringService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StringController {

    private final StringService stringService;

    @PostMapping("strings/repeat")
    public StringResponse repeat(@Valid @RequestBody StringRequest request) {

        return stringService.repeatString(request.getValue());
    }
}
