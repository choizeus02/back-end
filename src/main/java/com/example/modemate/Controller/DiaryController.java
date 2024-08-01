package com.example.modemate.Controller;

import com.example.modemate.DTO.DiaryAnalysisDTO;
import com.example.modemate.DTO.DiaryDTO;
import com.example.modemate.Security.custom.CustomUserDetails;
import com.example.modemate.Service.DiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
@Slf4j
public class DiaryController {

    private final DiaryService diaryService;

    private final RestTemplate restTemplate;

    //일기 쓰기 -> flask로 넘기기
    @PostMapping("/write")
    @Operation(summary = "일기 쓸때 필요한거", description = "일기에 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "실패", content = @Content(mediaType = "application/json"))
    })
    @Parameters({
            @Parameter(name = "month", description = "달", example = "march"),
            @Parameter(name = "time", description = "날짜", example = "2024-03-30"),
            @Parameter(name = "content", description = "일기내용", example = "오늘은 행복한 날이에요"),
            @Parameter(name = "analyze", description = "분석", example = "100 -50"),
            @Parameter(name = "emotion", description = "감정", example = "[\"happy\", \"sad\"]"),
    })
    public DiaryAnalysisDTO writeDiary(@AuthenticationPrincipal CustomUserDetails userDetails,
                                             @RequestBody DiaryDTO diaryDTO){

        log.info("[Diary Controller] write");

        String flaskServiceUrl = "http://localhost:5000/tokenize";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonBody = "{\"text\":\"" + diaryDTO.getContent() + "\"}";
        System.out.println(jsonBody);
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(flaskServiceUrl, HttpMethod.POST, requestEntity, String.class);

        String analyze = response.getBody();
        String positivePoint = analyze.split(" ")[0];
        String negativePoint = analyze.split(" ")[1];
        DiaryAnalysisDTO newAnalyz = diaryService.calculation(positivePoint, negativePoint);



        System.out.println(newAnalyz);
        diaryService.saveDiary(userDetails.getUsername(), newAnalyz, diaryDTO);
        return newAnalyz;

    }
    //일기 데이터 조회
    @GetMapping("/read")
    @Operation(summary = "일기 쓸때 필요한거", description = "일기에 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "실패", content = @Content(mediaType = "application/json"))
    })
    public List<DiaryDTO> findAllDiary(@AuthenticationPrincipal CustomUserDetails userDetails){

        log.info("[Diary Controller] read");

        List<DiaryDTO> diaryList = diaryService.findAllDiaryByNickname(userDetails.getUserId());

        return diaryList;
    }

}
