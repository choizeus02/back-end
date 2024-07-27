package com.example.modemate.DTO;

import com.example.modemate.domain.KeyWord;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProgramDTO {
    private Long id;
    private String name;
    private String time;
    private String place;
    private float rating;
    private List<KeyWordDto> keyWords;
}