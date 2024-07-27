package com.example.modemate.domain;


import com.example.modemate.enumration.ReviewType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    private String reviewText;

    private float rating;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private ReviewType reviewType;

    @Builder
    public Review(Long id, Program program, Video video, String reviewText, float rating, LocalDateTime createdAt, ReviewType reviewType) {
        this.id = id;
        this.program = program;
        this.video = video;
        this.reviewText = reviewText;
        this.rating = rating;
        this.createdAt = createdAt;
        this.reviewType = reviewType;
    }
}
