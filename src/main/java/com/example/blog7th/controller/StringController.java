package com.example.blog7th.controller;

import com.example.blog7th.dto.StringRequest;
import com.example.blog7th.dto.StringResponse;
import com.example.blog7th.service.StringService;
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
