package com.example.modemate.DTO;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDTO {

    private String email;

    private String password;

    public UserDTO(String email, String password){
        this.email = email;
        this.password = password;
    }
}