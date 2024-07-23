package com.example.modemate.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChatMessage {
    private String roomid; //방번호
    private String user; //메세지 보낸사람
    private String content; //메세지
    private LocalDateTime createAt;
}