package com.example.modemate.DTO;

import lombok.Getter;

@Getter
public class DiaryAnalysisDTO {
    String score;
    String analysisResults;

    public DiaryAnalysisDTO(String score, String analysisResults, String encouragementMessage) {
        this.score = score;
        this.analysisResults = analysisResults;
        this.encouragementMessage = encouragementMessage;
    }

    String encouragementMessage;
}
