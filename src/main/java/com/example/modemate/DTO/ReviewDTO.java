package com.example.modemate.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewDTO {
    private String reviewText;
    private float rating;
//        private ReviewKeyWord reviewKeyWord;
}
