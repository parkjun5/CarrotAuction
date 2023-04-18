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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ChatWebSocketHandler implements WebSocketHandler {

    private final Flux<ChatMessage> chatMessages;
    private final Sinks.Many<ChatMessage> chatSink;
    private final ObjectMapper mapper;
    private final Map<String, String> sessionMap;

    public ChatWebSocketHandler() {
        Sinks.Many<ChatMessage> processor = Sinks.many().replay().limit(10);
        this.chatMessages = processor.asFlux().onBackpressureLatest(); //pub
        this.chatSink = processor;
        this.mapper = new ObjectMapper();
        this.sessionMap = new ConcurrentHashMap<>();
    }
    
    //TODO sessionMap에 세션 아이디와 메시지 아이디를 저장하도록 변경
    // 그이후 채팅방 번호 확인해서 그방에만 뿌리도록 그리고 자기자신한테는 안뿌리도록 변경

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        Flux<ChatMessage> sessionMessages = handleReceivedMessages(session);

        Flux<WebSocketMessage> messageFlux = this.chatMessages
                .filter(chatMessage -> !chatMessage.getSenderId().equals(sessionMap.get(session.getId())))
                .mergeWith(sessionMessages)
                .onBackpressureLatest()
                .map(each -> session.textMessage(each.getMessage()));

        return session.send(messageFlux);
    }

    private Flux<ChatMessage> handleReceivedMessages(WebSocketSession session) {
        return session.receive()
                .map(socketMessage -> toChatMessage(socketMessage.getPayloadAsText()))
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(chatMessage -> {
                    this.sessionMap.put(session.getId(), chatMessage.getSenderId());
                    chatSink.tryEmitNext(chatMessage);
                })
                .thenMany(Flux.fromIterable(Collections.emptyList()));
    }


    private ChatMessage toChatMessage(String str) {
        try {
            return mapper.readValue(str, ChatMessage.class);
        } catch (JsonProcessingException e) {
            throw new ChatConvertException("채팅 값을 변환하다가 에러가 발생하였습니다." ,e);
        }
    }
}