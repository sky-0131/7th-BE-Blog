package com.example.blog7th.controller;

import com.example.blog7th.dto.StringRequest;
import com.example.blog7th.dto.StringResponse;
import com.example.blog7th.service.StringService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StringController {

    private final StringService stringService;

    @PostMapping("/string/repeat")
    public StringResponse repeat(@RequestBody StringRequest request) {

        return stringService.repeatString(request.getValue());
    }
}
