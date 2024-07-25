package com.example.modemate.DTO;

import com.example.modemate.domain.KeyWord;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgramEnrollRequestDTO {

    private String name;

    private Long counselor_id;

    private String time;

    private String place;

    private String details;

    private KeyWord keyWord;

}
