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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryService {
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

    public void saveDiary(String userName, DiaryDTO diaryDTO){
        User user = userRepository.findByNicknameOne(userName);
        Diary diary = new Diary(diaryDTO.getMonth(), diaryDTO.getDate(), diaryDTO.getContent(), diaryDTO.getAnalyze(), diaryDTO.getEmotion(), user);
        diaryRepository.save(diary);
    }

    public List<Diary> findAllDiaryByNickname(Long userId){
        List<Diary> diaryList = diaryRepository.findAllById(userId);
        return diaryList;
    }
}
