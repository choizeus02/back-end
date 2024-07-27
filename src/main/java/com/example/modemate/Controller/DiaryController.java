package com.example.modemate.Controller;

import com.example.modemate.DTO.DiaryDTO;
import com.example.modemate.Security.custom.CustomUserDetails;
import com.example.modemate.Service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    private final RestTemplate restTemplate;

    //일기 쓰기 -> flask로 넘기기
    @PostMapping("/write")
    public String writeDiary(@AuthenticationPrincipal CustomUserDetails userDetails,
                                             @RequestBody DiaryDTO diaryDTO){
        String flaskServiceUrl = "http://localhost:5000/tokenize";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonBody = "{\"text\":\"" + diaryDTO.getContent() + "\"}";
        System.out.println(jsonBody);
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(flaskServiceUrl, HttpMethod.POST, requestEntity, String.class);

        String analyze = response.getBody();
        System.out.println(analyze);
        diaryService.saveDiary(userDetails.getUsername(), analyze, diaryDTO);
        return analyze;

    }
    //일기 데이터 조회
    @GetMapping("/read")
    public List<DiaryDTO> findAllDiary(@AuthenticationPrincipal CustomUserDetails userDetails){
        List<DiaryDTO> diaryList = diaryService.findAllDiaryByNickname(userDetails.getUserId());
        return diaryList;
    }

}
