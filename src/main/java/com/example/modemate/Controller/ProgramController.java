package com.example.modemate.Controller;

import com.example.modemate.DTO.*;
import com.example.modemate.Repository.ProgramRepository;
import com.example.modemate.Repository.UserRepository;
import com.example.modemate.Security.jwt.JwtUtil;
import com.example.modemate.Service.ProgramService;
import com.example.modemate.domain.KeyWord;
import com.example.modemate.domain.Program;
import com.example.modemate.domain.Review;
import com.example.modemate.domain.User;
import com.example.modemate.enumration.ReviewType;
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

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/program")
@RequiredArgsConstructor
@Slf4j
public class ProgramController {

    private final ProgramService programService;
    private final UserRepository userRepository;
    private final ProgramRepository programRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/enroll")
    @Operation(summary = "프로그램 등록 기능", description = "프로그램 관련 API")
    @ApiResponse(responseCode = "200", description = "상담사 등록 성공", content = @Content(mediaType = "application/json"))
    @Parameters({
            @Parameter(name = "name", description = "프로그램 명", example = "그림 그리기로 알아보는 나의 심리상담사의 1:1 집중 분석 포함"),
            @Parameter(name = "counselor_id", description = "상담사 Id", example = "1"),
            @Parameter(name = "time", description = "시간", example = "7/30일 오후 2시"),
            @Parameter(name = "place", description = "장소", example = "한누리관 718호"),
            @Parameter(name = "details", description = "소개", example = "프로그램 설명들 ~!@~!"),
            @Parameter(name = "keyWord", description = "프로그램 키워드", example = "그림, 명상, 스트레스 관리, 긍정 에너지, 관계 회복, 자기이해, 불안관리, 우울증 극복, 트라우마 치료, 감정 조절, 불면증 극복, 자아성찰, 대인관계, "),
    })
    public String enroll(@Valid @RequestBody ProgramEnrollRequestDTO requestDTO)
            throws IOException {

        log.info("[Program Controller] enroll");

        Program program = programService.createProgram(requestDTO);

        return "success to enroll program : " + program.getName();

    }


    @PostMapping("/{programId}/add")
    @Operation(summary = "User의 Program등록 기능", description = "Program관련 API // header에 jwt 토큰 전송 필요")
    @ApiResponse(responseCode = "200", description = "프로그램 등록 성공", content = @Content(mediaType = "application/json"))
    public String add(@PathVariable Long programId, @RequestHeader("Authorization") String token) {

        log.info("[Program Controller] add programId");

        String jwt = token.substring(7);

        Long userId = jwtUtil.getUserId(jwt);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new RuntimeException("Program not found"));

        user.setProgram(program);
        userRepository.save(user);

        return "Program added to user successfully";


    }


    @GetMapping("/search")
    @Operation(summary = "프로그램 검색 기능 (param의 value값 공란 시 전체 조회; key값은 있어야함) ", description = "Program관련 API ")
    @ApiResponse(responseCode = "200", description = "프로그램 검색 성공", content = @Content(mediaType = "application/json"))
    @Parameters({
            @Parameter(name = "query", description = "검색할 프로그램의 이름 or keyword", example = "부부 상담 , 그림 그리기~  ")
    })
    public ResponseEntity<Result<List<ProgramDTO>>> search(@RequestParam(value = "query", required = false) String search) {

        log.info("[Program Controller] search by: " + search);

        List<Program> programs = programService.searchByNameOrKeyWord(search);

        List<ProgramDTO> collect = programs.stream()
                .map(program -> {
                    List<KeyWordDto> keyWordDto = program.getKeyWord().stream()
                            .map(keyword -> new KeyWordDto(keyword.getWord()))
                            .collect(Collectors.toList());

                    return new ProgramDTO(program.getId(),program.getName(), program.getTime(),
                            program.getPlace(), program.getRating(), keyWordDto);
                })
                .collect(Collectors.toList());

        Result<List<ProgramDTO>> result = new Result<>(collect);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/{programId}")
    @Operation(summary = "프로그램 상세 정보 기능", description = "Program관련 API ")
    @ApiResponse(responseCode = "200", description = "프로그램 상세 조회 성공", content = @Content(mediaType = "application/json"))
    public Result<ProgramDetailsDto> getProgramDetails(@PathVariable("programId") Long programId) {

        log.info("[Program Controller] program Details");

        Program program = programService.findProgram(programId);
        List<ReviewDTO> reviewDto = program.getReviews().stream()
                .map(review -> new ReviewDTO(review.getReviewText(), review.getRating()))
                .collect(Collectors.toList());

        ProgramDetailsDto programDetailsDto = new ProgramDetailsDto(
                program.getName(),
                program.getTime(),
                program.getPlace(),
                program.getRating(),
                program.getDetails(),
                reviewDto
        );

        return  new Result(programDetailsDto);
    }

    @PostMapping("/{programId}/addKeyWord")
    public ResponseEntity<String> register(@PathVariable Long programId, @RequestBody KeyWordDto requestDTO) {


        log.info("[Review Controller] add program review");

        KeyWord keyWord = programService.addKeyWord(programId,requestDTO);

        return ResponseEntity.ok("Review added successfully : " + keyWord);
    }

    @Data
    @AllArgsConstructor
    static class ProgramDetailsDto {
        private String name;
        private String time;
        private String place;
        private float rating;
        private String details;
        private List<ReviewDTO> reviews;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;


    }

}