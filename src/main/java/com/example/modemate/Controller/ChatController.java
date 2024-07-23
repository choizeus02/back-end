package com.example.modemate.Controller;

import com.example.modemate.DTO.ChatRoomDTO;
import com.example.modemate.DTO.ChatRoomDetailDTO;
import com.example.modemate.Security.jwt.JwtUtil;
import com.example.modemate.Service.ChatService;
import com.example.modemate.domain.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    public String createRoom(@RequestParam String yournickname,
                             @CookieValue("token") String token) {
        String mynickName = util.getUserNickname(token);
        System.out.print(mynickName);
        return chatService.createChatRoom(mynickName, yournickname);
    }

    @GetMapping("/main")
    public List<ChatRoomDTO> getChatRooms(@CookieValue("token") String token){
        String myNickname = util.getUserNickname(token);
        System.out.println(myNickname);
        List<ChatRoomDTO> chatingRooms = chatService.getChatingRooms(myNickname);
        for (ChatRoomDTO chatingRoom : chatingRooms) {
            System.out.println("chatingRoom = " + chatingRoom);
        }
        return chatingRooms;
    }

    @GetMapping("/all")
    public List<ChatRoom> getAllRooms(){
        return chatService.getAllRooms();
    }

    @GetMapping("/find")
    public ChatRoomDetailDTO findRoom(@RequestParam String roomId,
                                      @CookieValue(value = "token") String token){
        String nickname = util.getUserNickname(token);
        Long userId = util.getUserId(token);
        return chatService.findRoom(roomId, nickname, userId);
    }

}