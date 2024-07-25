package com.example.modemate.DTO;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDTO {

    private String email;

    private String password;

    private String nickname;

    public UserDTO(String email, String password, String nickname){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}