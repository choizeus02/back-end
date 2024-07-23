package com.example.modemate.Security.custom;


import com.example.modemate.enumration.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomUserInfoDto {
    private Long userId;

    private String email;

    private String password;

    private String nickname;
}
