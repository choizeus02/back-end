package com.example.modemate.Controller;

import com.example.modemate.DTO.ReviewRegisterRequest2DTO;
import com.example.modemate.DTO.ReviewRegisterRequestDTO;
import com.example.modemate.Service.ProgramService;
import com.example.modemate.Service.ReviewService;
import com.example.modemate.domain.Review;
import com.example.modemate.enumration.ReviewType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;
    private final ProgramService programService;

    @PostMapping("/program/{programId}/reviews")
    @Operation(summary = "프로그램 리뷰 등록 기능", description = "리뷰 관련 API")
    @ApiResponse(responseCode = "200", description = "리뷰 등록 성공", content = @Content(mediaType = "application/json"))
    @Parameters({
            @Parameter(name = "reviewText", description = "리뷰 내용", example = "인상적입니다~"),
            @Parameter(name = "rating", description = "별점", example = "4.5 (float)"),
    })
    public ResponseEntity<String> register(@PathVariable Long programId, @RequestBody ReviewRegisterRequestDTO requestDTO) {


        // 로그인 jwt토큰 확인하여 로그인 Id 추출
        // 로그인 id확인 후 해당 programId가 수강 목록에 있는지 확인

        log.info("[Review Controller] add program review");
        requestDTO.setReviewType(ReviewType.PROGRAM);

        Review review = reviewService.addReview(programId,requestDTO);
        programService.updateRating(programId);

        return ResponseEntity.ok("Review added successfully : " + review);
    }

    @PostMapping("/video/{videoId}/reviews")
    @Operation(summary = "영상 리뷰 등록 기능", description = "리뷰 관련 API")
    @ApiResponse(responseCode = "200", description = "리뷰 등록 성공", content = @Content(mediaType = "application/json"))
    @Parameters({
            @Parameter(name = "reviewText", description = "리뷰 내용", example = "인상적입니다~"),
            @Parameter(name = "rating", description = "별점", example = "4.5 (float)"),
    })
    public ResponseEntity<String> register2(@PathVariable Long videoId, @RequestBody ReviewRegisterRequest2DTO requestDTO) {

        log.info("[Review Controller] add video review");
        requestDTO.setReviewType(ReviewType.VIDEO);

        Review review = reviewService.addReview2(videoId,requestDTO);

        return ResponseEntity.ok("Review added successfully : " + review);
    }
}
