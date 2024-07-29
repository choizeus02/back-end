package com.example.modemate.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ChatRoom{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public ChatRoom(String roomId, User user, Counselor opponentUser) {
        this.roomId = roomId;
        this.user = user;
        this.opponentUser = opponentUser;
    }

    private String roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "my_user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "opponent_user_id")
    private Counselor opponentUser;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY)
    private List<ChatHistory> histories = new ArrayList<>();
}