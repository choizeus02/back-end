package com.example.modemate.Controller;

import com.example.modemate.DTO.DiaryDTO;
import com.example.modemate.Security.custom.CustomUserDetails;
import com.example.modemate.Service.DiaryService;
import com.example.modemate.domain.Diary;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    private final RestTemplate restTemplate;

    //일기 쓰기 -> flask로 넘기기
    @GetMapping("/write")
    public String writeDiary(@AuthenticationPrincipal CustomUserDetails userDetails,
                                             @RequestParam DiaryDTO diaryDTO){
        String flaskServiceUrl = "http://localhost:5000/tokenize";
        ResponseEntity<String> response = restTemplate.postForEntity(flaskServiceUrl, diaryDTO.getContent(), String.class);
        String analyze = response.getBody();
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
