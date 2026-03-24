package com.example.blog7th.service;

import com.example.blog7th.dto.StringResponse;
import org.springframework.stereotype.Service;

@Service
public class StringService {
    public StringResponse repeatString(String value) {
        return new StringResponse(value, value);
    }
}
