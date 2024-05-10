package com.carrot.core.chat.domain.repository;

import com.carrot.core.chat.domain.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {
    @Query("select ch from ChatHistory ch where ch.chatRoom.id = :chatRoomId")
    List<ChatHistory> findChatHistoriesByChatRoomId(@Param("chatRoomId") Long chatRoomId);
}
