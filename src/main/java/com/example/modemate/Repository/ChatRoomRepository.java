package com.example.modemate.Repository;


import com.example.modemate.domain.ChatRoom;
import com.example.modemate.domain.Counselor;
import com.example.modemate.domain.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByUserAndOpponentUser(User user, User opponentUser);
  
    //    @Query("select cr from ChatRoom cr where cr.roomId = : roomId")
    ChatRoom findByRoomId(@Param("roomId") String roomId);

    @Query("SELECT cr FROM ChatRoom cr WHERE cr.user.id = :user OR cr.opponentUser.id = :user")
    List<ChatRoom> findByUserOrOpponentUser(@Param("user") Long user);

    @Modifying
    @Query(value = "delete from ChatRoom cr where cr.user_id = :id or cr.opponent_user = :id", nativeQuery = true)
    void deleteByUserOrOpponentuser(@Param("id") Long id);
}