package com.example.modemate.Service;

import com.example.modemate.Controller.ProgramController;
import com.example.modemate.DTO.KeyWordDto;
import com.example.modemate.DTO.ProgramEnrollRequestDTO;
import com.example.modemate.Repository.CounselorRepository;
import com.example.modemate.Repository.KeyWordRepository;
import com.example.modemate.Repository.ProgramRepository;
import com.example.modemate.domain.Counselor;
import com.example.modemate.domain.KeyWord;
import com.example.modemate.domain.Program;
import com.example.modemate.domain.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.Key;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProgramService {


    private final CounselorRepository counselorRepository;
    private final ProgramRepository programRepository;
    private final KeyWordRepository keyWordRepository;

    public Program findProgram(Long programId) {

        log.info("[Program Service] find program");


        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new RuntimeException("Program not found"));

        return program;
    }


    @Transactional
    public Program createProgram(ProgramEnrollRequestDTO requestDTO) throws IOException {

        log.info("[Program Service] create program");


        Counselor counselor = counselorRepository.findById(requestDTO.getCounselor_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid counselor ID"));

        Program program = Program.builder()
                .name(requestDTO.getName())
                .counselor(counselor)
                .time(requestDTO.getTime())
                .place(requestDTO.getPlace())
                .details(requestDTO.getDetails())
                .build();


        return programRepository.save(program);
    }


    public List<Program> searchByName(String name) {
        log.info("[Program Service] find program for search");

        return programRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional
    public void updateRating(Long programId) {
        log.info("[Program Service] update rating");

        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid counselor ID"));


        List<Review> reviews = program.getReviews();
        if (reviews.isEmpty()) {
            log.warn("No reviews found for program ID: {}", programId);
            return;
        }

        double averageRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);

        float roundedRating = Math.round(averageRating * 10) / 10.0f;

        program.setRating(roundedRating);
        System.out.println(program.getRating());
        programRepository.save(program);

        log.info("Updated program rating to {}", averageRating);
    }

    public List<Program> searchByNameOrKeyWord(String search) {
        return programRepository.findByNameOrKeyWord(search);
    }


    @Transactional
    public KeyWord addKeyWord(Long programId, KeyWordDto requestDTO) {

        log.info("[Program Service] add program keyword");

        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new RuntimeException("Program not found"));

        KeyWord keyWord = KeyWord.builder()
                .program(program)
                .word(requestDTO.getWord())
                .build();

        return keyWordRepository.save(keyWord);

    }
}
