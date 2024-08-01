package com.example.modemate.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "diary")
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String month;

    public Diary(String month, String time, String content, String ana, String analysisMessage, String encouragementMessage, List<String> emotion, User user) {
        this.month = month;
        this.time = time;
        this.content = content;
        this.ana = ana;
        this.analysisMessage = analysisMessage;
        this.encouragementMessage = encouragementMessage;
        this.emotion = emotion;
        this.user = user;
    }

    private String time;

    private String content;

    private String ana;

    private String analysisMessage;

    private String encouragementMessage;

    @ElementCollection
    @CollectionTable(name = "emotion", joinColumns =
    @JoinColumn(name = "diary_id")
    )
    private List<String> emotion = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
