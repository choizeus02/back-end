package com.example.modemate.Controller;

import com.example.modemate.DTO.UserDTO;
import com.example.modemate.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "회원 로그인 기능", description = "로그인에 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "로그인 실패", content = @Content(mediaType = "application/json"))
    })
    @Parameters({
            @Parameter(name = "email", description = "아이디(이메일)", example = "test@naver.com"),
            @Parameter(name = "password", description = "비밀번호", example = "1234"),
    })
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        String status = userService.login(userDTO);

        if(status.equals("user not found") || status.equals("password error")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(status);
        }

        return ResponseEntity.status(HttpStatus.OK).body(status);
    }


    @PostMapping("/register")
    @Operation(summary = "회원 가입 기능", description = "회원가입에 사용되는 API")
    @ApiResponse(responseCode = "200", description = "회원 가입 성공", content = @Content(mediaType = "application/json"))
    @Parameters({
            @Parameter(name = "email", description = "아이디(이메일)", example = "user2@naver.com"),
            @Parameter(name = "password", description = "비밀번호", example = "1234"),
    })
    public String register(@RequestBody UserDTO dto) {
        userService.register(dto);
        return userService.login(new UserDTO(dto.getEmail(), dto.getPassword(), dto.getNickname()));
    }

}
