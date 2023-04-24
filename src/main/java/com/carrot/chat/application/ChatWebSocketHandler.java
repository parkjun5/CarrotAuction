package com.carrot.chat.application;


import com.carrot.chat.domain.ChatMessage;
import com.carrot.chat.exception.ChatConvertException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.*;
import reactor.core.scheduler.Schedulers;

import java.util.*;


@Slf4j
@Component
public class ChatWebSocketHandler implements WebSocketHandler {

    private final Flux<Object> chatMessages;
    private final Sinks.Many<Object> chatSink;
    private final ObjectMapper mapper;
    private static final String JSESSION_ID = "JSESSION_ID";

    public ChatWebSocketHandler(Sinks.Many<Object> sinks, Flux<Object> chatMessages) {
        this.chatMessages = chatMessages;
        this.chatSink = sinks;
        this.mapper = new ObjectMapper();
    }

    @Override
    public Mono<Void> handle( WebSocketSession session) {
        if (!afterConnectionLoginValidate(session))
            return session.send(Flux.just(session.textMessage("로그인은 필수입니다."))).then();

        handleReceivedMessages(session);

        return this.chatMessages
                .map(String::valueOf)
                .map(session::textMessage)
                .as(session::send);
    }

    private boolean afterConnectionLoginValidate(WebSocketSession session) {
        List<String> sessionId = session.getHandshakeInfo().getHeaders().get(JSESSION_ID);
        if (sessionId == null)
            return false;

        String loginId = sessionId.stream().findAny().orElseThrow(IllegalArgumentException::new);

        if (session.isOpen()) {
            chatSink.emitNext(loginId + "님이 채팅방에 입장하였습니다.", Sinks.EmitFailureHandler.FAIL_FAST);
        }
        return true;
    }

    private void handleReceivedMessages(WebSocketSession session) {
        session.receive()
                .map(socketMessage -> toChatMessage(socketMessage.getPayloadAsText()))
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(chatSink::tryEmitNext)
                .doOnTerminate(() -> {
                    List<String> sessionId = session.getHandshakeInfo().getHeaders().get(JSESSION_ID);
                    assert sessionId != null;
                    String userId = sessionId.stream().findAny().orElseThrow(IllegalArgumentException::new);
                    chatSink.emitNext(userId + "님이 채팅방에서 나갔습니다!", Sinks.EmitFailureHandler.FAIL_FAST);
                })
                .subscribe(inMsg -> log.info("Received inbound message from client" + inMsg.getSenderId() + " : " + inMsg.getMessage()));
    }

    private ChatMessage toChatMessage(String str) {
        try {
            return mapper.readValue(str, ChatMessage.class);
        } catch (JsonProcessingException e) {
            throw new ChatConvertException("채팅 값을 변환하다가 에러가 발생하였습니다." ,e);
        }
    }
}