package com.example.modemate.domain;


import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {

    private String employmentHistory; // 경력

    private String scholarship; // 학력

    private String currentJob; // 현재 근무

    private String specialization; // 전문 분야

    private String affiliation; // 소속


    @Builder
    public Profile(String employmentHistory, String scholarship, String currentJob, String specialization, String affiliation) {
        this.employmentHistory = employmentHistory;
        this.scholarship = scholarship;
        this.currentJob = currentJob;
        this.specialization = specialization;
        this.affiliation = affiliation;
    }
}
