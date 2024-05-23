package com.carrot.chat.websocket.chatmessage.application;

import com.carrot.chat.queue.application.RedisPubService;
import com.carrot.chat.queue.ui.MessageObject;
import com.carrot.chat.websocket.common.exception.ChatConvertException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.time.LocalDateTime;

@Slf4j
@Component
public class ChatMessageMapper {

    private final ObjectMapper objectMapper;
    private final RedisPubService redisPubService;
    public ChatMessageMapper(ObjectMapper objectMapper, RedisPubService redisPubService) {
        this.objectMapper = objectMapper;
        this.redisPubService = redisPubService;
    }

    public String serialize(MessageObject messageObject) {
        try {
            return objectMapper.writeValueAsString(messageObject);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(messageObject.toString(), e);
        }
    }

    public String serialize(String value, String alertMessage) {
        String message = value + alertMessage;
        MessageObject messageObject = new MessageObject("CHAT", message, LocalDateTime.now(), 1L,
                "관리자", "", 777L);

        try {
            return objectMapper.writeValueAsString(messageObject);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(messageObject.toString(), e);
        }
    }

    public WebSocketMessage serializeAndAddTo(MessageObject messageObject, WebSocketSession session) {
        try {
            String payload = objectMapper.writeValueAsString(messageObject);
            return session.textMessage(payload);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(messageObject.toString(), e);
        }
    }

    public MessageObject toMessage(String payloadAsText) {
        try {
            return objectMapper.readValue(payloadAsText, MessageObject.class);
        } catch (JsonProcessingException e) {
            log.error("Chat Convert Exception : " + payloadAsText);
            throw new ChatConvertException("채팅 값을 변환하다가 에러가 발생하였습니다.", e);
        }
    }

    public MessageObject toMessageWith(String payloadAsText, String sessionId) {
        try {
            MessageObject messageObject = objectMapper.readValue(payloadAsText, MessageObject.class);
            String writerName = redisPubService.getWriterNameById(messageObject.userId());
            return messageObject.changeInfo(writerName, sessionId);
        } catch (JsonProcessingException e) {
            log.error("Chat Convert Exception : " + payloadAsText);
            throw new ChatConvertException("채팅 값을 변환하다가 에러가 발생하였습니다.", e);
        }
    }

}
