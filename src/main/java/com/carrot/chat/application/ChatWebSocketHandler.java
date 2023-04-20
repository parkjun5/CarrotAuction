package com.carrot.chat.application;


import com.carrot.chat.domain.ChatMessage;
import com.carrot.chat.exception.ChatConvertException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.*;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class ChatWebSocketHandler implements WebSocketHandler {

    private final Flux<ChatMessage> chatMessages;
    private final Sinks.Many<ChatMessage> chatSink;
    private final ObjectMapper mapper;
    private final Map<String, String> sessionMap;

    public ChatWebSocketHandler() {
        Sinks.Many<ChatMessage> processor = Sinks.many().replay().limit(3);
        this.chatMessages = processor.asFlux().onBackpressureBuffer(3, BufferOverflowStrategy.DROP_OLDEST);
        this.chatSink = processor;
        this.mapper = new ObjectMapper();
        this.sessionMap = new ConcurrentHashMap<>();
    }

    @Override
    public Mono<Void> handle( WebSocketSession session) {
        handleReceivedMessages(session);

        Flux<WebSocketMessage> messageFlux = this.chatMessages
                .filter(chatMessage -> {
                    String sessionId = session.getId();
                    String values = sessionMap.get(chatMessage.getSenderId());
                    return values != null && !Arrays.asList(values.split(",")).contains(sessionId);
                })
                .map(each -> session.textMessage(String.valueOf(each)));

        return session.send(messageFlux);
    }

    private void handleReceivedMessages(WebSocketSession session) {
        session.receive()
                .map(socketMessage -> toChatMessage(socketMessage.getPayloadAsText()))
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(chatMessage -> {
                    sessionMap.compute(chatMessage.getSenderId(), (k,v) -> v == null? session.getId() : v + "," + session.getId());
                    chatSink.tryEmitNext(chatMessage);
                })
                .subscribe();
    }

    private ChatMessage toChatMessage(String str) {
        try {
            return mapper.readValue(str, ChatMessage.class);
        } catch (JsonProcessingException e) {
            throw new ChatConvertException("채팅 값을 변환하다가 에러가 발생하였습니다." ,e);
        }
    }
}