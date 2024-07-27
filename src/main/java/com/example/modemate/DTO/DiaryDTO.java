package com.example.modemate.DTO;

import com.example.modemate.domain.User;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DiaryDTO {
    private String month;
    private String time;
    private String content;
    private String analyze;
    private List<String> emotion;

    public DiaryDTO(String month, String time, String content, String analyze, List<String> emotion) {
        this.month = month;
        this.time = time;
        this.content = content;
        this.analyze = analyze;
        this.emotion = emotion;
    }

}
