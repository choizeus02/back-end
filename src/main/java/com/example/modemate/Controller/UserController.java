package com.example.modemate.Controller;

import com.example.modemate.DTO.TokenDTO;
import com.example.modemate.DTO.UserLoginDTO;
import com.example.modemate.DTO.UserRegisterDTO;
import com.example.modemate.Repository.UserRepository;
import com.example.modemate.Service.UserService;
import com.example.modemate.domain.Program;
import com.example.modemate.domain.User;
import com.example.modemate.Security.jwt.JwtUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    @Operation(summary = "회원 로그인 기능", description = "이메일(email), 패스워드로(password)를 이용하여 로그인 시도")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "로그인 실패", content = @Content(mediaType = "application/json"))
    })
    @Parameters({
            @Parameter(name = "email", description = "아이디(이메일)", example = "test@naver.com"),
            @Parameter(name = "password", description = "비밀번호", example = "1234"),
    })
    public ResponseEntity<TokenDTO> login(@RequestBody UserLoginDTO userDTO) {

        log.info("[User Controller] login");
        String token = userService.login(userDTO);

        if ("user not found".equals(token) || "password error".equals(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        TokenDTO tokenResponse = new TokenDTO(token);


        return ResponseEntity.status(HttpStatus.OK).body(tokenResponse);
    }


    @PostMapping("/register")
    @Operation(summary = "회원 가입 기능", description = "이메일(email), 별칭(nickname), 비밀번호(password)를 이용하여 회원가입")
    @ApiResponse(
            responseCode = "200",
            description = "회원 가입 성공 및 JWT 토큰 반환",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class)
            )
    )
    @Parameters({
            @Parameter(name = "email", description = "아이디(이메일)", example = "user2@naver.com"),
            @Parameter(name = "nickname", description = "별칭", example = "수몽이"),
            @Parameter(name = "password", description = "비밀번호", example = "1234"),
    })
    public ResponseEntity<TokenDTO>  register(@RequestBody UserRegisterDTO dto) {
        log.info("[User Controller] register");

        userService.register(dto);

        String token = userService.login(new UserLoginDTO(dto.getEmail(), dto.getPassword()));
        TokenDTO tokenResponse = new TokenDTO(token);


        return ResponseEntity.status(HttpStatus.OK).body(tokenResponse);
    }

    @GetMapping("/findNickname")
    @Operation(summary = "User의 nickname 조회 기능", description = "User관련 API // header에 jwt 토큰 전송 필요")
    @ApiResponse(responseCode = "200", description = "닉네임 조회 성공", content = @Content(mediaType = "application/json"))
    public ResponseEntity<NicknameDto> add(@RequestHeader("Authorization") String token) {

        log.info("[User Controller] find nickname");

        String jwt = token.substring(7);

        Long userId = jwtUtil.getUserId(jwt);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        NicknameDto nicknameDto = new NicknameDto(user.getNickname(),user.getCounselorId());


        return ResponseEntity.ok(nicknameDto);

    }


    @Data
    public class NicknameDto {
        private String nickname;
        private Long counselorId;

        public NicknameDto(String nickname, Long counselorId) {
            this.nickname = nickname;
            this.counselorId = counselorId;
        }
    }

}
