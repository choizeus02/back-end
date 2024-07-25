package com.example.modemate.DTO;

import com.example.modemate.domain.Video;
import com.example.modemate.enumration.ReviewType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewRegisterRequest2DTO {


    private Video video;

    private String reviewText;

    private float rating;

    ReviewType reviewType;

}
