package com.example.modemate.Service;

import com.example.modemate.DTO.DiaryAnalysisDTO;
import com.example.modemate.DTO.DiaryDTO;
import com.example.modemate.Repository.DiaryRepository;
import com.example.modemate.Repository.UserRepository;
import com.example.modemate.domain.Diary;
import com.example.modemate.domain.User;
import com.example.modemate.init.MessagePair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public void saveDiary(String userName, DiaryAnalysisDTO analyze, DiaryDTO diaryDTO){

        log.info("[Diary Service] save");

        User user = userRepository.findByNickname1(userName);
        Diary diary = new Diary(diaryDTO.getMonth(), diaryDTO.getTime(), diaryDTO.getContent(), analyze.getScore(), analyze.getAnalysisResults(), analyze.getEncouragementMessage(), diaryDTO.getEmotion(), user);
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

    public DiaryAnalysisDTO calculation(String positivePoint, String negativePoint){
        int negativeScore = Math.abs(Integer.parseInt(negativePoint));
        int positiveScore = Integer.parseInt(positivePoint);

        int totalScore = positiveScore + negativeScore;

        double positivePercentage = ((double) positiveScore / totalScore) * 100;
        double negativePercentage = ((double) negativeScore / totalScore) * 100;

        String positivePercentageStr = String.format("%.2f", positivePercentage);
        String negativePercentageStr = String.format("%.2f", negativePercentage);

        String result = positivePercentageStr + " " + negativePercentageStr;

        List<MessagePair> messages = new ArrayList<>();

        messages.add(new MessagePair(
                "오늘 일기에서는 많은 부정적인 감정이 느껴졌어요. 힘든 상황이나 고민들이 많이 있었던 것 같아요. 이런 날들은 누구에게나 찾아오지만, 잘 이겨낼 수 있을 거라고 믿어요.",
                "지금 많이 힘드시겠지만, 당신은 충분히 이겨낼 수 있는 사람입니다. 모든 어려움은 결국 지나가기 마련이에요. 어둠 속에서도 빛을 찾을 수 있는 당신의 용기를 믿어요. 당신의 하루하루는 소중하고, 그런 당신이 이겨낼 수 있도록 우리는 항상 응원할게요. 힘내세요, 당신은 혼자가 아닙니다."
        ));

        messages.add(new MessagePair(
                "오늘 하루는 어려운 점이 많았던 것 같아요. 부정적인 감정이 많이 표현되었네요. 하지만 그 속에서도 작은 긍정적인 요소를 발견하려는 노력이 엿보여요.",
                "당신의 노력은 언제나 값지고 중요합니다. 작은 변화부터 시작해보세요. 더 나은 내일이 당신을 기다리고 있어요. 때로는 작은 발걸음 하나가 큰 변화를 만들어냅니다. 오늘 하루의 어려움을 잘 이겨냈으니, 내일은 더 밝을 거예요. 우리는 당신의 모든 발걸음을 응원합니다. 화이팅!"
        ));

        messages.add(new MessagePair(
                "오늘은 어려운 일이 많았지만, 그 속에서도 긍정적인 부분을 찾으려고 노력한 것 같아요. 부정적인 감정 속에서도 희망을 놓지 않으려는 모습이 인상적이었어요.",
                "지금의 어려움은 당신을 더 강하게 만들 거예요. 조금만 더 힘내세요. 당신의 노력은 결코 헛되지 않을 겁니다. 작은 희망의 불씨가 큰 불꽃으로 타오를 날이 올 거예요. 당신의 오늘은 내일의 당신을 위한 값진 경험이 될 것입니다. 항상 응원할게요."
        ));

        messages.add(new MessagePair(
                "오늘 하루는 힘든 일이 있었지만, 그 와중에도 긍정적인 요소를 찾으셨군요. 다양한 감정들이 공존하는 하루였네요.",
                "힘든 순간에도 희망을 잃지 않는 당신의 모습이 멋져요. 조금씩 더 나아질 거예요. 당신은 충분히 잘하고 있습니다. 힘든 순간마다 당신의 노력은 큰 힘이 될 거예요. 당신의 오늘이 더 나은 내일을 위한 발판이 될 것이라 믿어요. 힘내세요!"
        ));

        messages.add(new MessagePair(
                "오늘은 긍정과 부정이 균형을 이루는 날이었어요. 다양한 감정이 공존했지만, 긍정적인 부분을 놓치지 않으셨네요.",
                "긍정적인 마음을 잃지 않는 당신이 대단합니다. 작은 기쁨들을 소중히 여기며, 조금씩 더 나아가는 당신을 응원합니다. 당신의 긍정적인 태도는 큰 변화를 가져올 수 있습니다. 오늘 하루의 균형 잡힌 감정이 내일의 더 큰 행복으로 이어질 거예요. 당신은 충분히 멋진 사람입니다."
        ));

        messages.add(new MessagePair(
                "오늘은 긍정적인 생각이 조금 더 많았어요. 그래도 힘든 일도 있었겠죠? 하지만 긍정적인 부분을 잃지 않으려는 모습이 인상적이에요.",
                "긍정적인 마음가짐이 더해지면 더 좋은 일들이 생길 거예요. 지금처럼 계속 앞으로 나아가세요. 당신은 잘하고 있어요. 오늘의 긍정적인 생각들이 내일의 큰 행복으로 돌아올 거라 믿어요. 당신의 작은 변화들이 큰 변화를 이끌어낼 것입니다. 항상 응원합니다."
        ));

        messages.add(new MessagePair(
                "오늘 하루는 긍정적인 면이 더 많았네요. 부정적인 감정도 있었지만 잘 이겨내셨군요. 전체적으로 밝은 하루를 보내신 것 같아요.",
                "당신의 긍정적인 태도는 주변 사람들에게도 큰 힘이 될 거예요. 지금처럼 꾸준히 나아가세요. 당신은 빛나는 존재입니다. 오늘 하루의 긍정적인 에너지가 당신의 미래를 더욱 밝게 만들 거예요. 힘든 순간에도 희망을 잃지 않는 당신이 정말 자랑스럽습니다."
        ));

        messages.add(new MessagePair(
                "오늘은 대체로 긍정적인 하루를 보냈어요. 부정적인 감정도 있었지만 크게 영향받지 않은 것 같아요. 하루 종일 좋은 일이 많았던 것 같아요.",
                "당신의 긍정적인 에너지가 주변을 밝히고 있어요. 힘든 일이 생겨도 잘 이겨낼 수 있는 당신이 자랑스러워요. 계속 이렇게 나아가세요! 당신의 밝은 마음이 주변 사람들에게 큰 영향을 미치고 있습니다. 오늘 하루의 행복이 내일의 더 큰 기쁨으로 이어지길 바라요. 당신은 정말 멋져요!"
        ));

        messages.add(new MessagePair(
                "오늘은 매우 긍정적인 하루를 보냈어요. 작은 어려움도 있었지만 잘 극복했네요. 대체로 즐겁고 만족스러운 하루였던 것 같아요.",
                "당신의 긍정적인 에너지 덕분에 모든 일이 잘 풀릴 거예요. 지금처럼 항상 밝고 긍정적인 모습을 유지하세요. 당신은 정말 멋져요! 오늘 하루의 작은 행복들이 내일의 더 큰 기쁨으로 돌아올 거라 믿어요. 당신의 밝은 마음이 주변에도 큰 기쁨을 줄 것입니다."
        ));

        messages.add(new MessagePair(
                "오늘은 거의 모든 것이 긍정적이었어요. 하루가 행복하고 즐거웠던 것 같아요. 당신의 긍정적인 에너지가 가득 느껴집니다.",
                "당신의 밝은 마음이 주변 사람들에게도 큰 행복을 주고 있어요. 항상 이렇게 행복하고 긍정적인 하루를 보내길 바라요. 당신은 최고의 하루를 보내고 있어요! 오늘의 긍정적인 에너지가 내일의 더 큰 기쁨으로 이어지길 바랍니다. 당신의 행복한 하루가 계속되길 응원합니다."
        ));

        int index = (int)(positivePercentage / 10);
        if (index >= messages.size()) {
            index = messages.size() - 1;
        }
        MessagePair selectedMessage = messages.get(index);
        DiaryAnalysisDTO diaryAnalysisDTO = new DiaryAnalysisDTO(result, selectedMessage.getAnalysisMessage(), selectedMessage.getEncouragementMessage());
        return diaryAnalysisDTO;
    }
}
