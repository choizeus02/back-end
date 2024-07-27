package com.example.modemate.DTO;


import com.example.modemate.domain.Profile;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CounselorRegisterRequestDTO {

    private String name;

    private String comment;

    private Profile profile;

    private String category;


    @Builder
    public CounselorRegisterRequestDTO(String name, String comment, Profile profile, String category) {
        this.name = name;
        this.comment = comment;
        this.profile = profile;
        this.category = category;
    }
}
