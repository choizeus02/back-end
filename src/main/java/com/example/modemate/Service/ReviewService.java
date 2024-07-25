package com.example.modemate.Service;



import com.example.modemate.DTO.ReviewRegisterRequest2DTO;
import com.example.modemate.DTO.ReviewRegisterRequestDTO;
import com.example.modemate.Repository.ProgramRepository;
import com.example.modemate.Repository.ReviewRepository;
import com.example.modemate.Repository.VideoRepository;
import com.example.modemate.domain.Program;
import com.example.modemate.domain.Review;
import com.example.modemate.domain.Video;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ProgramRepository programRepository;
    private final ReviewRepository reviewRepository;
    private final VideoRepository videoRepository;

    @Transactional
    public Review addReview(Long programId, ReviewRegisterRequestDTO requestDTO) {

        log.info("[Review Service] add program Review");

        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new RuntimeException("Program not found"));


        Review review = Review.builder()
                .program(program)
                .reviewText(requestDTO.getReviewText())
                .rating(requestDTO.getRating())
                .createdAt(LocalDateTime.now())
                .reviewType(requestDTO.getReviewType())
                .build();

        return reviewRepository.save(review);
    }

    @Transactional
    public Review addReview2(Long videoId, ReviewRegisterRequest2DTO requestDTO) {

        log.info("[Review Service] add video Review");

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        Review review = Review.builder()
                .video(video)
                .reviewText(requestDTO.getReviewText())
                .rating(requestDTO.getRating())
                .createdAt(LocalDateTime.now())
                .reviewType(requestDTO.getReviewType())
                .build();

        return reviewRepository.save(review);
    }

}
