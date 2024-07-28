package com.example.modemate.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Details {

    private String title;
    private String time;
    private String content;


    public Details(String title, String time, String content) {
        this.title = title;
        this.time = time;
        this.content = content;
    }
}
