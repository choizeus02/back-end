package com.example.modemate.Controller;

import com.example.modemate.DTO.ChatRoomDTO;
import com.example.modemate.DTO.ChatRoomDetailDTO;
import com.example.modemate.Security.custom.CustomUserDetails;
import com.example.modemate.Security.jwt.JwtUtil;
import com.example.modemate.Service.ChatService;
import com.example.modemate.domain.ChatRoom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final JwtUtil util;

    //방만들기
    @GetMapping("/room")
    @Operation(summary = "방 만들기", description = "채팅방에 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "방 만들기 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "방 만들기 실패", content = @Content(mediaType = "application/json"))
    })
    @Parameters({
            @Parameter(name = "yournickname", description = "받는 사람 닉네임", example = "test1"),
    })
    public String createRoom(@RequestParam String yournickname,
                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        System.out.print(userDetails.getUsername());
        return chatService.createChatRoom(userDetails.getUsername(), yournickname);
    }

    @GetMapping("/main")
    @Operation(summary = "채팅방 전체 조회", description = "채팅방에 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "방 만들기 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "방 만들기 실패", content = @Content(mediaType = "application/json"))
    })
    public List<ChatRoomDTO> getChatRooms(@AuthenticationPrincipal CustomUserDetails userDetails){
        System.out.println(userDetails.getUsername());
        List<ChatRoomDTO> chatingRooms = chatService.getChatingRooms(userDetails.getUsername());
        for (ChatRoomDTO chatingRoom : chatingRooms) {
            System.out.println("chatingRoom = " + chatingRoom);
        }
        return chatingRooms;
    }

    @GetMapping("/all")
    @Operation(summary = "그냥 전체 채팅방 다 조회", description = "채팅에 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "실패", content = @Content(mediaType = "application/json"))
    })
    public List<ChatRoom> getAllRooms(){
        return chatService.getAllRooms();
    }

    @GetMapping("/find")
    @Operation(summary = "채팅방 들어갈때 필요한거", description = "채팅에 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "실패", content = @Content(mediaType = "application/json"))
    })
    @Parameters({
            @Parameter(name = "roomId", description = "아이디(이메일)", example = "test@naver.com"),
    })
    public ChatRoomDetailDTO findRoom(@RequestParam String roomId,
                                      @AuthenticationPrincipal CustomUserDetails userDetails){
        String nickname = userDetails.getUsername();
        Long userId = userDetails.getUserId();
        return chatService.findRoom(roomId, nickname, userId);
    }

}