package com.example.modemate.Service;

import com.example.modemate.DTO.DiaryDTO;
import com.example.modemate.Repository.DiaryRepository;
import com.example.modemate.Repository.UserRepository;
import com.example.modemate.domain.Diary;
import com.example.modemate.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class DiaryService {
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

    @Transactional
    public void saveDiary(String userName, String analyze, DiaryDTO diaryDTO){

        log.info("[Diary Service] save");

        User user = userRepository.findByNickname1(userName);
        diaryDTO.setAnalyze(analyze);
        Diary diary = new Diary(diaryDTO.getMonth(), diaryDTO.getTime(), diaryDTO.getContent(), diaryDTO.getAnalyze(), diaryDTO.getEmotion(), user);
        diaryRepository.save(diary);
    }

    public List<DiaryDTO> findAllDiaryByNickname(Long userId){

        log.info("[Diary Service] find");

        List<Diary> diaryList = diaryRepository.findByUserId(userId);
        List<DiaryDTO> diaryDTOList = diaryList
                .stream()
                .map(diary -> new DiaryDTO(
                        diary.getMonth(),
                        diary.getTime(),
                        diary.getContent(),
                        diary.getAna(),
                        diary.getEmotion()
                ))
                .collect(Collectors.toList());
        return diaryDTOList;
    }
}
