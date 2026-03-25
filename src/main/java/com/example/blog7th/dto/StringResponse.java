package com.example.blog7th.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@AllArgsConstructor

public class StringResponse {

    @JsonProperty("string_one")
    private String stringOne;

    @JsonProperty("string_two")
    private String stringTwo;
}
