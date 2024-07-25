package com.example.modemate.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"reviews", "members"})
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "counselor_id", nullable = false)
    private Counselor counselor;

    private String time;

    private String place;

    private String details;

    private float rating;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private List<KeyWord> keyWord;

//    @Enumerated(EnumType.STRING)
//    private KeyWord keyWord;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private List<User> users;

    @Builder
    public Program(Long id, String name, Counselor counselor, String time, String place, String details, float rating, List<KeyWord> keyWord, List<Review> reviews, List<User> users) {
        this.id = id;
        this.name = name;
        this.counselor = counselor;
        this.time = time;
        this.place = place;
        this.details = details;
        this.rating = rating;
        this.keyWord = keyWord;
        this.reviews = reviews;
        this.users = users;
    }
}

