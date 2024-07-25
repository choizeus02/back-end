package com.example.modemate.Controller;

import com.example.modemate.DTO.DiaryDTO;
import com.example.modemate.Security.custom.CustomUserDetails;
import com.example.modemate.Service.DiaryService;
import com.example.modemate.domain.Diary;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    //일기 쓰기 -> flask로 넘기기
    public void writeDiary(@AuthenticationPrincipal CustomUserDetails userDetails,
                           @RequestParam DiaryDTO diaryDTO){
        diaryService.saveDiary(userDetails.getUsername(), diaryDTO);
        //데이터 가공처리 후 저장
    }
    //일기 데이터 조회
    public List<Diary> findAllDiary(@AuthenticationPrincipal CustomUserDetails userDetails){
        List<Diary> diaryList = diaryService.findAllDiaryByNickname(userDetails.getUserId());
        return diaryList;
    }

}
