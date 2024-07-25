package com.example.modemate.Service;

import com.example.modemate.DTO.UserDTO;
import com.example.modemate.Repository.UserRepository;
import com.example.modemate.Security.custom.CustomUserInfoDto;
import com.example.modemate.Security.jwt.JwtUtil;
import com.example.modemate.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    @Transactional
    public void register(UserDTO userDTO) {
        User user = new User(userDTO.getEmail(), encoder.encode(userDTO.getPassword()), userDTO.getNickname());
        userRepository.save(user);
    }
    public String login(UserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow();

        if(user == null) {
            return "user not found";
        }

        if(!encoder.matches(userDTO.getPassword(), user.getPassword())) {
            return "password error";
        }

        CustomUserInfoDto customUserInfoDto = new CustomUserInfoDto(user.getId(), user.getEmail(), user.getPassword(), user.getNickname());
        return jwtUtil.createAccessToken(customUserInfoDto);
    }
}
