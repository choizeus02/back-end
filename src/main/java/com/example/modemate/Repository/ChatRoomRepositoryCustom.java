package com.example.modemate.Repository;

import com.example.modemate.DTO.ChatRoomDetailDTO;

public interface ChatRoomRepositoryCustom {
    ChatRoomDetailDTO findDetailDtoByRoomId();
}