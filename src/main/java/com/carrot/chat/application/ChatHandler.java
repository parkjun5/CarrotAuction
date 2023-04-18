package com.carrot.chat.application;


import com.carrot.chat.domain.ChatMessage;
import com.carrot.chat.exception.ChatConvertException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.*;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;


public class ChatHandler implements WebSocketHandler {

    private final Flux<ChatMessage> chatMessages;
    private final Sinks.Many<ChatMessage> chatSink;
    private final ObjectMapper mapper;

    public ChatHandler() {
        Sinks.Many<ChatMessage> processor = Sinks.many().replay().limit(10);
        this.chatMessages = processor.asFlux().onBackpressureLatest(); //pub
        this.chatSink = processor;
        this.mapper = new ObjectMapper();
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {

        AtomicLong roomId = new AtomicLong();

        Flux<ChatMessage> sessionMessages = session.receive()
                .map(socketMessage -> toChatMessage(socketMessage.getPayloadAsText()))
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(chatMessage -> {
                    roomId.set(chatMessage.getChatRoomId());
                    chatSink.tryEmitNext(chatMessage);
                })
                .thenMany(Flux.fromIterable(Collections.emptyList()));

        Flux<WebSocketMessage> messageFlux = this.chatMessages
                .mergeWith(sessionMessages)
                .onBackpressureLatest()
                .filter(each -> roomId.longValue() == each.getChatRoomId())
                .map(each -> session.textMessage(each.getMessage()));

        return session.send(messageFlux);
    }

    private ChatMessage toChatMessage(String str) {
        try {
            return mapper.readValue(str, ChatMessage.class);
        } catch (JsonProcessingException e) {
            throw new ChatConvertException("채팅 값을 변환하다가 에러가 발생하였습니다." ,e);
        }
    }
}