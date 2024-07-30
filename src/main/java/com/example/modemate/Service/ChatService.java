package com.example.modemate.Service;


import com.example.modemate.DTO.ChatHistoryResponse;
import com.example.modemate.DTO.ChatMessage;
import com.example.modemate.DTO.ChatRoomDTO;
import com.example.modemate.DTO.ChatRoomDetailDTO;
import com.example.modemate.Repository.ChatHistoryRepository;
import com.example.modemate.Repository.ChatRoomRepository;
import com.example.modemate.Repository.CounselorRepository;
import com.example.modemate.Repository.UserRepository;
import com.example.modemate.domain.ChatHistory;
import com.example.modemate.domain.ChatRoom;
import com.example.modemate.domain.Counselor;
import com.example.modemate.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository memberRepository;
    private final ChatHistoryRepository chatHistoryRepository;
    private final CounselorRepository counselorRepository;

    @Transactional
    public String createChatRoom(String myNickName, String yourNickName) {
        User user = getUserByNickname(myNickName); //User에서 찾아야 하는거고
        User opponentUser = getUserByNickname(yourNickName); //Coordinator에서 찾아야 하는거고

        Optional<ChatRoom> chatRoom = findChatRoom(user, opponentUser);
        ChatRoom result;

        if(!chatRoom.isPresent()){
            result = create(user ,opponentUser);
            chatRoomRepository.save(result);
        } else {
            result = chatRoom.get();
        }

        return result.getRoomId();
    }

    public Long saveMessage(ChatMessage message) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(message.getRoomid());
        User sender = getUserByNickname(message.getUser());
        String _message = message.getContent();
        return saveChatMessage(chatRoom, sender ,_message, message.getCreateAt());
    }
    @Transactional
    public Long saveChatMessage(ChatRoom chatRoom, User sender, String message, LocalDateTime createdAt) {
        ChatHistory chatHistory = ChatHistory.create(chatRoom, sender, message, createdAt);
        chatHistoryRepository.save(chatHistory);
        // 준형
        Long id = chatHistory.getId();
        return id;
    }
    @Transactional
    public ChatRoom create(User user, User opponentUser) {
        ChatRoom chatRoom = new ChatRoom(UUID.randomUUID().toString(), user, opponentUser);
        return chatRoom;
    }


    private Optional<ChatRoom> findChatRoom(User user, User opponentUser) {
        return chatRoomRepository.findByUserAndOpponentUser(user, opponentUser);
    }

    public User getUserByNickname(String name) {
        List<User> members = memberRepository.findByNickname(name);
        return members.isEmpty() ? null : members.get(0);
    }

    @Transactional
    public void readChat(Long chatid){
        ChatHistory chatHistory = chatHistoryRepository.findById(chatid).get();
        if(chatHistory != null){
            System.out.println("chatHistory = " + chatHistory);
            chatHistory.setReadCount(chatHistory.getReadCount() - 1);
        }
    }

    public List<ChatRoomDTO> getChatingRooms(String nickname){
        User user = getUserByNickname(nickname);
        List<ChatRoom> chatRoom = chatRoomRepository.findByUserOrOpponentUser(user.getId());

        List<ChatRoomDTO> chatRoomDTOs = chatRoom
                .stream()
                .map(chatRoom1 -> new ChatRoomDTO(
                        chatRoom1.getRoomId(),
                        user.getNickname(),
                        chatRoom1.getUser().getNickname().equals(nickname)  ?
                                chatRoom1.getOpponentUser().getNickname() : chatRoom1.getUser().getNickname() ,
                        chatRoom1.getHistories().stream().map(
                                chatHistory -> new ChatHistoryResponse(
                                        chatHistory.getSender().getNickname(),
                                        chatHistory.getReadCount(),
                                        chatHistory.getMessage(),
                                        chatHistory.getCreatedAt())).toList())).toList();
        return chatRoomDTOs;
    }
    public List<ChatRoom> getAllRooms(){
        return chatRoomRepository.findAll();
    }

    public ChatRoomDetailDTO findRoom(String roomId, String nickname, Long userId){
        // 채팅방 들어갈 때 readCount 벌크 연산
        int count = chatHistoryRepository.bulkReadCount(userId);

        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);

        User user = chatRoom.getUser();
        User opponentUser = !user.getNickname().equals(nickname) ? user : chatRoom.getOpponentUser();

        return new ChatRoomDetailDTO(chatRoom.getRoomId(),
                nickname,
                opponentUser.getNickname(),
                chatRoom.getHistories()
                        .stream()
                        .map(chatHistory ->
                                new ChatHistoryResponse(
                                        chatHistory.getSender().getNickname(),
                                        chatHistory.getReadCount(),
                                        chatHistory.getMessage(),
                                        chatHistory.getCreatedAt())).toList());

    }
    @Transactional
    public void deletHistoryAndChatRoom(Long id){
        chatHistoryRepository.deleteBySender(id);
        chatRoomRepository.deleteByUserOrOpponentuser(id);
    }

}