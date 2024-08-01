package com.example.modemate.init;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MessagePair {
    String analysisMessage;
    String encouragementMessage;

    public MessagePair(String analysisMessage, String encouragementMessage) {
        this.analysisMessage = analysisMessage;
        this.encouragementMessage = encouragementMessage;
    }

    @Override
    public String toString() {
        return "Analysis: " + analysisMessage + "\nEncouragement: " + encouragementMessage;
    }
}
