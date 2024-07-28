package com.example.modemate.init;

import com.example.modemate.Repository.UserRepository;
import com.example.modemate.domain.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

//    @PostConstruct
//    public void init() {
//        User user1 = new User("test@naver.com", encoder.encode("1234"), "test1");
//        User user = new User("test0@naver.com", encoder.encode("1234"), "test2");
//
//        userRepository.save(user1);
//        userRepository.save(user);
//    }
}