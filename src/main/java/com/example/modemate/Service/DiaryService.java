package com.example.modemate.Service;

import com.example.modemate.DTO.DiaryDTO;
import com.example.modemate.Repository.DiaryRepository;
import com.example.modemate.Repository.UserRepository;
import com.example.modemate.domain.Diary;
import com.example.modemate.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryService {
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

    @Transactional
    public void saveDiary(String userName, String analyze, DiaryDTO diaryDTO){
        User user = userRepository.findByNickname1(userName);
        diaryDTO.setAnalyze(analyze);
        Diary diary = new Diary(diaryDTO.getMonth(), diaryDTO.getTime(), diaryDTO.getContent(), diaryDTO.getAnalyze(), diaryDTO.getEmotion(), user);
        diaryRepository.save(diary);
    }

    public List<DiaryDTO> findAllDiaryByNickname(Long userId){
        List<Diary> diaryList = diaryRepository.findAllById(userId);
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
