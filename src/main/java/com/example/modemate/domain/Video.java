package com.example.modemate.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Long id;

    private String title;

    private String name;

    private String url;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    private List<Review> reviews;


    @Builder
    public Video(Long id, String title, String name, String url, List<Review> reviews) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.url = url;
        this.reviews = reviews;
    }
}

