package com.example.modemate.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Counselor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Counselor_id")
    private Long id;

    private String name;

    private String comment;

    @Embedded
    private Profile profile;

    private String category;

    @OneToMany(mappedBy = "counselor", cascade = CascadeType.ALL)
    private List<Program> programs;

    private String imgUrl;


    @Builder
    public Counselor(Long id, String name, String comment, Profile profile, String category, List<Program> programs, String imgUrl) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.profile = profile;
        this.category = category;
        this.programs = programs;
        this.imgUrl = imgUrl;
    }
}
