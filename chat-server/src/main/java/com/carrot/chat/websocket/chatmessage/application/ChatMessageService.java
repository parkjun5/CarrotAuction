package com.carrot.chat.websocket.chatmessage.application;

import com.carrot.chat.websocket.chatmessage.domain.ChatMessage;
import com.carrot.chat.websocket.chatmessage.domain.ChatMessageRepository;
import com.carrot.chat.websocket.common.exception.ChatConvertException;
import com.carrot.chat.websocket.chatmessage.application.sequence.SequenceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Slf4j
@Profile("mongodb")
@Service
@RequiredArgsConstructor
public class ChatMessageService {

    /**
     * 제공된 sinks와 chatMessages를 사용하여 ChatWebSocketHandler를 초기화합니다.
     *
     * @param sinks        채팅 메시지 처리를 위한 Sinks.Many 인스턴스
     * @param chatMessages 채팅 메시지 처리를 위한 Flux 인스턴스
     */

    private static final String JSESSION_ID = "JSESSION_ID";
    private final ChatMessageRepository chatRoomRepository;
    private final SequenceService sequenceService;
    private final ObjectMapper mapper;
    private final Sinks.Many<Object> chatSink;

    public Flux<ChatMessage> findAll() {
        return chatRoomRepository.findAll();
    }

    public Flux<ChatMessage> findChatMessageBySenderId(String senderId) {
        return chatRoomRepository.findAllBySenderId(senderId);
    }

    public void saveChatMessage(ChatMessage chatMessage) {
        chatMessage.setId(sequenceService.generateSeqByName(ChatMessage.SEQUENCE_NAME));
        chatRoomRepository.save(chatMessage).subscribe();
    }

    public Mono<ChatMessage> makeTestMessages() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(sequenceService.generateSeqByName(ChatMessage.SEQUENCE_NAME));
        chatMessage.setMessage(UUID.randomUUID().toString());
        chatMessage.setSenderId("발신자");
        chatMessage.setChatRoomId(0L);
        return chatRoomRepository.save(chatMessage);

    }

    public Mono<Void> deleteAll() {
        return chatRoomRepository.deleteAll();
    }

    /**
     * 연결 후 로그인 검증을 수행합니다.
     * 세션에서 JSESSION_ID를 확인하여 로그인이 되어 있는지 판단하고,
     * 로그인이 되어 있다면 채팅방 입장 메시지를 전송합니다.
     *
     * @param session WebSocket 세션
     * @return 로그인이 유효한 경우 true, 그렇지 않으면 false
     */
    public boolean afterConnectionValidate(WebSocketSession session) {
        List<HttpCookie> httpCookies = session.getHandshakeInfo().getCookies().get(JSESSION_ID);
        if (httpCookies == null) return false;

        HttpCookie httpCookie = httpCookies
                .stream()
                .filter(Objects::nonNull)
                .filter(f -> f.getName().equals(JSESSION_ID)).findAny()
                .orElse(null);

        if (httpCookie == null) return false;

        if (session.isOpen())
            chatSink.emitNext(serializeChatMessage(httpCookie.getValue(), "님이 채팅방에 입장하였습니다."), Sinks.EmitFailureHandler.FAIL_FAST);
        return true;
    }

    public void sendChatMessage(ChatMessage chatMessage) {
        try {
            chatSink.tryEmitNext(mapper.writeValueAsString(chatMessage));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(chatMessage.toString() ,e);
        }
    }

    public void afterConnectionTerminate(WebSocketSession session) {
        List<HttpCookie> httpCookies = session.getHandshakeInfo().getCookies().get(JSESSION_ID);
        String value = httpCookies.stream().filter(f -> f.getName().equals(JSESSION_ID)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("JSESSION_ID 가 존재하지 않습니다."))
                .getValue();
        chatSink.emitNext(serializeChatMessage(value, "님이 채팅방에서 나갔습니다!"), Sinks.EmitFailureHandler.FAIL_FAST);
    }

    /**
     * 문자열을 ChatMessage 객체로 변환합니다.
     * 변환 중 오류가 발생하면 ChatConvertException을 발생시킵니다.
     *
     * @param payloadAsText JSON 형식의 문자열
     * @return 변환된 ChatMessage 객체
     * @throws ChatConvertException 변환 중 오류 발생 시
     */
    public ChatMessage toChatMessage(String payloadAsText) {
        try {
            return mapper.readValue(payloadAsText, ChatMessage.class);
        } catch (JsonProcessingException e) {
            log.error("Chat Convert Exception : " + payloadAsText);
            throw new ChatConvertException("채팅 값을 변환하다가 에러가 발생하였습니다." ,e);
        }
    }

    private String serializeChatMessage(String value, String alertMessage) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(value + alertMessage);
        try {
            return mapper.writeValueAsString(chatMessage);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(chatMessage.toString() ,e);
        }
    }
}
