package com.example.modemate.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private Long id;

    private String time;

    private String content;

    private String analyze;

    @ElementCollection
    private List<String> emotion = new ArrayList<>();
}
