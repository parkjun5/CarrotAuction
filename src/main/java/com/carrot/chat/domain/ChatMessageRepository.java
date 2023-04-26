package com.carrot.chat.domain;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ChatMessageRepository extends ReactiveMongoRepository<ChatMessage, Long> {

    Flux<ChatMessage> findChatMessageById(Long id);

}
