package com.example.modemate.Controller;

import com.example.modemate.DTO.ReviewDTO;
import com.example.modemate.DTO.VideoEnrollRequestDTO;
import com.example.modemate.Service.VideoService;
import com.example.modemate.domain.Program;
import com.example.modemate.domain.Video;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/video")
@RequiredArgsConstructor
@Slf4j
public class VideoController {

    private final VideoService videoService;

    @PostMapping("/enroll")
    @Operation(summary = "비디오 등록 기능", description = "비디오 관련 API")
    @ApiResponse(responseCode = "200", description = "비디오 등록 성공", content = @Content(mediaType = "application/json"))
    @Parameters({
            @Parameter(name = "title", description = "제목", example = "유튜브 제목"),
            @Parameter(name = "name", description = "유튜버 이름", example = "김철수"),
            @Parameter(name = "url", description = "url", example = "."),
    })
    public String enroll(@Valid @RequestBody VideoEnrollRequestDTO requestDTO)
            throws IOException {

        log.info("[Program Controller] enroll");

        Video video = videoService.createVideo(requestDTO);

        return "success to enroll program : " + video.getTitle();

    }

    @GetMapping("/")
    @Operation(summary = "비디오 조회 기능", description = "비디오 관련 API")
    @ApiResponse(responseCode = "200", description = "비디오 조회 성공", content = @Content(mediaType = "application/json"))
    @Parameters({
            @Parameter(name = "title", description = "제목", example = "유튜브 제목"),
            @Parameter(name = "name", description = "유튜버 이름", example = "김철수"),
            @Parameter(name = "url", description = "url", example = "."),
    })
    public Result findVideo() {

        log.info("[Video Controller] findAll");

        List<Video> findVideo = videoService.findAllVideo();
        List<VideoDto> collect = findVideo.stream()
                .map(m -> new VideoDto(m.getId(),m.getTitle(),m.getName(),m.getUrl()))
                .collect(Collectors.toList());

        return new Result(collect);

    }

    @GetMapping("/{videoId}")
    @Operation(summary = "비디오 상세 조회 기능", description = "비디오 관련 API")
    @ApiResponse(responseCode = "200", description = "비디오 조회 성공", content = @Content(mediaType = "application/json"))
    public Result getVideoDetails(@PathVariable("videoId") Long videoId) {

        log.info("[Video Controller] video Details");

        Video video = videoService.findVideoById(videoId);
        List<ReviewDTO> reviewDto = video.getReviews().stream()
                .map(review -> new ReviewDTO(review.getReviewText(), review.getRating()))
                .collect(Collectors.toList());

        VideoDetailsDto videoDto = new VideoDetailsDto(
                video.getTitle(),
                video.getName(),
                video.getUrl(),
                reviewDto
        );



        return new Result(videoDto);

    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }


    @Data
    @AllArgsConstructor
    static class VideoDto {
        private Long id;
        private String title;
        private String name;
        private String url;
    }
    @Data
    @AllArgsConstructor
    static class VideoDetailsDto {
        private String title;
        private String name;
        private String url;
        private List<ReviewDTO> reviews;

    }
}
