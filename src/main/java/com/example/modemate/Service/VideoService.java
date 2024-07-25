package com.example.modemate.Service;

import com.example.modemate.DTO.VideoEnrollRequestDTO;
import com.example.modemate.Repository.VideoRepository;
import com.example.modemate.domain.Video;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class VideoService {

    private final VideoRepository videoRepository;

    @Transactional
    public Video createVideo(VideoEnrollRequestDTO requestDTO) {

        log.info("[Video Service] create video");

        Video video = Video.builder()
                .title(requestDTO.getTitle())
                .name(requestDTO.getName())
                .url(requestDTO.getUrl())
                .build();

        return videoRepository.save(video);
    }

    public List<Video> findAllVideo() {

        log.info("[Video Service] search All videos ");
        return videoRepository.findAll();

    }

    public Video findVideoById(Long videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));


    }
}
