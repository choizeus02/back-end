package com.example.modemate.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChatRoomDTO {
    private String roomId;
    private String user;
    private String opponentUser;
    private List<ChatHistoryResponse> histories;

    public ChatRoomDTO(String roomId, String user, String opponentUser, List<ChatHistoryResponse> histories) {
        this.roomId = roomId;
        this.user = user;
        this.opponentUser = opponentUser;
        this.histories = histories;
    }
}