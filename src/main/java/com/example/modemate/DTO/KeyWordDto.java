package com.example.modemate.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class KeyWordDto {
    private String word;

    @JsonCreator
    public KeyWordDto(String word) {
        this.word = word;
    }
}