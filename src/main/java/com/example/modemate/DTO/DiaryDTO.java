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

    private String date;

    private String content;

    private String analyze;

    private List<String> emotion = new ArrayList<>();
}
