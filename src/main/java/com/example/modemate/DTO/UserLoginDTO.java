package com.example.modemate.DTO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginDTO {

    private String email;

    private String password;


    public UserLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

}