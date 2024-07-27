package com.example.modemate.DTO;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRegisterDTO {

    private String email;

    private String nickname;

    private String password;


    public UserRegisterDTO(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

}