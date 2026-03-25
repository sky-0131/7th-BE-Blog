package com.example.blog7th.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1")
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "ok";
    }
}