package com.carrot.reactive.chatmessage.domain;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ChatMessageRepository extends ReactiveMongoRepository<ChatMessage, Long> {

    @Query("{ 'senderId' : ?0 }")
    Flux<ChatMessage> findAllBySenderId(String senderId);

}
