package com.example.modemate.DTO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VideoEnrollRequestDTO {
    private String title;

    private String name;

    private String url;

}
