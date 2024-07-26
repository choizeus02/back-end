package com.example.modemate.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Diary(String month, String date, String content, String analyze, List<String> emotion, User user) {
        this.month = month;
        this.date = date;
        this.content = content;
        this.analyze = analyze;
        this.emotion = emotion;
        this.user = user;
    }

    private String month;

    private String date;

    private String content;

    private String analyze;

    @ElementCollection
    private List<String> emotion = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
