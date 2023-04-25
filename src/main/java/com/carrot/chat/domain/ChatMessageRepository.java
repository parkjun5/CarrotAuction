package com.carrot.chat.domain;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ChatMessageRepository extends ReactiveCrudRepository<ChatMessage, Long> {

    @Query("select ch from ChatMessage ch where ch.chatRoomId = :roomId")
    Flux<ChatMessage> findChatMessageById(@Param("roomId") Long roomId);

}
