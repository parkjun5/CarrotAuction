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
    private static final String JSESSION_ID = "JSESSION_ID";
    public static final String LOGIN_REQUIRED_MESSAGE = "로그인은 필수입니다.";
    private final Flux<Object> chatMessages;
    private final Sinks.Many<Object> chatSink;
    private final ObjectMapper mapper;
    private final ChatService chatService;

    /**
     * 제공된 sinks와 chatMessages를 사용하여 ChatWebSocketHandler를 초기화합니다.
     *
     * @param sinks        채팅 메시지 처리를 위한 Sinks.Many 인스턴스
     * @param chatMessages 채팅 메시지 처리를 위한 Flux 인스턴스
     */
    public ChatWebSocketHandler(Sinks.Many<Object> sinks, Flux<Object> chatMessages, ChatService chatService) {
        this.chatMessages = chatMessages;
        this.chatSink = sinks;
        this.mapper = new ObjectMapper();
        this.chatService = chatService;
    }

    /**
     * WebSocket 연결이 수립되면 호출되는 메소드입니다.
     * 연결된 클라이언트에 대해 로그인 검증을 수행하고,
     * 이후 메시지 처리 및 클라이언트에 메시지 전송을 처리합니다.
     *
     * @param session WebSocket 세션
     * @return Mono<Void> WebSocket 처리가 완료되면 종료되는 Mono
     */
    @Override
    public Mono<Void> handle( WebSocketSession session) {
        if (!afterConnectionLoginValidate(session))
            return session.send(Flux.just(session.textMessage(LOGIN_REQUIRED_MESSAGE))).then();

        processReceivedMessages(session);

        return this.chatMessages
                .map(String::valueOf)
                .map(session::textMessage)
                .as(session::send);
    }

    /**
     * 연결 후 로그인 검증을 수행합니다.
     * 세션에서 JSESSION_ID를 확인하여 로그인이 되어 있는지 판단하고,
     * 로그인이 되어 있다면 채팅방 입장 메시지를 전송합니다.
     *
     * @param session WebSocket 세션
     * @return 로그인이 유효한 경우 true, 그렇지 않으면 false
     */
    private boolean afterConnectionLoginValidate(WebSocketSession session) {
        List<String> sessionIds = session.getHandshakeInfo().getHeaders().get(JSESSION_ID);
        if (sessionIds == null)
            return false;

        String loginId = sessionIds.stream().findAny()
                .orElseThrow(() -> new IllegalArgumentException("JSESSION_ID 가 존재하지 않습니다."));

        if (session.isOpen()) {
            chatSink.emitNext(loginId + "님이 채팅방에 입장하였습니다.", Sinks.EmitFailureHandler.FAIL_FAST);
        }
        return true;
    }

    /**
     * 클라이언트로부터 받은 메시지를 처리합니다.
     * 메시지를 ChatMessage 객체로 변환하고,
     * 종료 시 채팅방 퇴장 메시지를 전송합니다.
     *
     * @param session WebSocket 세션
     */
    private void processReceivedMessages(WebSocketSession session) {
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
                .subscribe(chatService::saveChatMessage);
    }

    /**
     * 문자열을 ChatMessage 객체로 변환합니다.
     * 변환 중 오류가 발생하면 ChatConvertException을 발생시킵니다.
     *
     * @param str JSON 형식의 문자열
     * @return 변환된 ChatMessage 객체
     * @throws ChatConvertException 변환 중 오류 발생 시
     */
    private ChatMessage toChatMessage(String str) {
        try {
            return mapper.readValue(str, ChatMessage.class);
        } catch (JsonProcessingException e) {
            log.error("Chat Convert Exception : " + str);
            throw new ChatConvertException("채팅 값을 변환하다가 에러가 발생하였습니다." ,e);
        }
    }
}