package com.example.modemate.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.beans.ConstructorProperties;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KeyWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id")
    private Long id;

    private String word;

    @ManyToOne
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @Builder
    public KeyWord(Long id, String word, Program program) {
        this.id = id;
        this.word = word;
        this.program = program;
    }

}
