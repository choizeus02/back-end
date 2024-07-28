package com.example.modemate.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

@Data
public class TokenDTO {
    private String token;

    @JsonCreator
    public TokenDTO(String token) {
        this.token = token;
    }
}