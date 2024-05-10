package com.carrot.chat.websocket.chatmessage.ui;

import com.carrot.chat.queue.application.RedisContainerManager;
import com.carrot.chat.queue.application.RedisPubService;
import com.carrot.chat.queue.ui.MessageObject;
import com.carrot.chat.websocket.common.exception.ChatConvertException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.net.URI;
import java.time.LocalDateTime;

@Slf4j
@Component
public class ChatWebSocketClientHandler implements WebSocketHandler {
    private final Flux<Object> chatMessagesSink;
    private final Sinks.Many<Object> sinks;
    private final ObjectMapper objectMapper;
    private final RedisPubService redisPubService;
    private final RedisContainerManager redisContainerManager;


    public ChatWebSocketClientHandler(Flux<Object> chatMessagesSink, Sinks.Many<Object> sinks,
                                      RedisPubService redisPubService, RedisContainerManager redisContainerManager) {
        this.chatMessagesSink = chatMessagesSink;
        this.sinks = sinks;
        this.redisPubService = redisPubService;
        this.redisContainerManager = redisContainerManager;
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.objectMapper = mapper;
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
    public Mono<Void> handle(WebSocketSession session) {
        if (session.isOpen()) {
            URI uri = session.getHandshakeInfo().getUri();
            String newUser = redisContainerManager.addSubscriber(uri);
            sinks.emitNext(serializeChatMessage(newUser, "님이 채팅방에 입장하였습니다."), Sinks.EmitFailureHandler.FAIL_FAST);
        }

        Flux<WebSocketMessage> outgoingMessages = chatMessagesSink
                .map(it -> toMessageObject(it.toString()))
                .filter(it -> !it.sessionId().equals(session.getId()))
                .map(it -> serializeAndConvert(it, session))
                .doOnNext(message -> log.info("Message Received: {}", message.getPayloadAsText()))
                .doOnError(error -> log.error("Error in receiving messages: ", error));


        Flux<WebSocketMessage> incomingMessages = session.receive()
                .map(socketMessage -> {
                    MessageObject messageObject = toMessageObject(socketMessage.getPayloadAsText());
                    String writerName = redisPubService.getWriterNameById(messageObject.userId());
                    return messageObject.changeWriteInfo(writerName, session.getId());
                })
                .doOnNext(it -> {
                    sendChatMessage(it);
                    redisPubService.sendMessage(it);
                })
                .map(it -> serializeAndConvert(it, session))
                .doOnNext(message -> log.info("Message Received: {}", message.getPayloadAsText()))
                .doOnError(error -> log.error("Error in receiving messages: ", error));

        Flux<WebSocketMessage> combinedMessages = Flux.merge(outgoingMessages, incomingMessages);

        return session.send(combinedMessages)
                .doOnTerminate(() -> afterConnectionTerminate(session))
                .doOnError(error -> log.error("WebSocket error: ", error));
    }

    public MessageObject toMessageObject(String payloadAsText) {
        try {
            return objectMapper.readValue(payloadAsText, MessageObject.class);
        } catch (JsonProcessingException e) {
            log.error("Chat Convert Exception : " + payloadAsText);
            throw new ChatConvertException("채팅 값을 변환하다가 에러가 발생하였습니다.", e);
        }
    }

    public void sendChatMessage(MessageObject messageObject) {
        try {
            sinks.tryEmitNext(objectMapper.writeValueAsString(messageObject));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(messageObject.toString(), e);
        }
    }

    public void afterConnectionTerminate(WebSocketSession session) {
        URI uri = session.getHandshakeInfo().getUri();
        String newUser = redisContainerManager.removeSubscriber(uri);
        sinks.emitNext(serializeChatMessage(newUser, "님이 채팅방에서 나갔습니다!"), Sinks.EmitFailureHandler.FAIL_FAST);
    }


    private String serializeChatMessage(String value, String alertMessage) {
        MessageObject chatMessage = new MessageObject(value + alertMessage,
                LocalDateTime.now(), 1L, "관리자", "", 777L);
        try {
            return objectMapper.writeValueAsString(chatMessage);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(chatMessage.toString(), e);
        }
    }

    private WebSocketMessage serializeAndConvert(MessageObject messageObject, WebSocketSession session) {
        try {
            String payload = objectMapper.writeValueAsString(messageObject);
            return session.textMessage(payload);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(messageObject.toString(), e);
        }
    }

}

