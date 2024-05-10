//package com.carrot.chat.websocket.chatmessage.ui;
//
//import com.carrot.chat.queue.application.RedisContainerManager;
//import com.carrot.chat.queue.application.RedisPubService;
//import com.carrot.chat.queue.ui.MessageObject;
//import com.carrot.chat.websocket.common.exception.ChatConvertException;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.socket.WebSocketHandler;
//import org.springframework.web.reactive.socket.WebSocketMessage;
//import org.springframework.web.reactive.socket.WebSocketSession;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//import static org.reflections.Reflections.log;
//
//@Component
//public class WebSocketChatHandler implements WebSocketHandler {
//    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
//    private final RedisPubService redisPubService;
//    private final RedisContainerManager redisContainerManager;
//    private final ObjectMapper objectMapper;
//
//    public WebSocketChatHandler(RedisPubService redisPubService, RedisContainerManager redisContainerManager, ObjectMapper objectMapper) {
//        this.redisPubService = redisPubService;
//        this.redisContainerManager = redisContainerManager;
//        this.objectMapper = objectMapper;
//    }
//
//    @Override
//    public Mono<Void> handle(WebSocketSession session) {
//        String sessionId = session.getId();
//        if (session.isOpen()) {
//            sessions.put(sessionId, session);
//            URI uri = session.getHandshakeInfo().getUri();
//            String newUser = redisContainerManager.addSubscriber(uri, sessionId);
//            sendMessageToClient(session, newUser + "님이 채팅방에 입장하였습니다.");
//        }
//
//
//        return session.receive()
//                .map(WebSocketMessage::getPayloadAsText)
//                .doOnNext(message -> processMessage(message, session))
//                .doFinally(it -> cleanup(session)).then();
//    }
//
//    private void processMessage(String message, WebSocketSession session) {
//        MessageObject messageObject = toMessageObject(message);
//        String sessionId = session.getId();
//        redisPubService.sendMessage(messageObject, sessionId);
//    }
//
//    private Mono<Void> sendMessageToClient(WebSocketSession session, String message) {
//        return session.send(Flux.just(session.textMessage(message))).then();
//    }
//
//    private Mono<Void> cleanup(WebSocketSession session) {
//        String sessionId = session.getId();
//        sessions.remove(sessionId);
//        redisContainerManager.removeSubscriber(session.getHandshakeInfo().getUri(), sessionId);
//        return sendMessageToClient(session, "님이 채팅방에서 나갔습니다!");
//    }
//
//    public MessageObject toMessageObject(String payloadAsText) {
//        try {
//            return objectMapper.readValue(payloadAsText, MessageObject.class);
//        } catch (JsonProcessingException e) {
//            throw new ChatConvertException("채팅 값을 변환하다가 에러가 발생하였습니다.", e);
//        }
//    }
//
//    public Mono<Void> sendOutgoingMessages(String sessionId, MessageObject messageObject) {
//        WebSocketSession session = sessions.get(sessionId);
//        if (session != null && session.isOpen()) {
//            String serializedMessage;
//            try {
//                serializedMessage = objectMapper.writeValueAsString(messageObject);
//            } catch (JsonProcessingException e) {
//                return Mono.error(new ChatConvertException("Failed to serialize message", e));
//            }
//
//            return session.send(Mono.just(session.textMessage(serializedMessage)))
//                    .doOnError(e -> log.error("Failed to send message: " + e.getMessage()))
//                    .onErrorResume(e -> Mono.empty());
//        } else {
//            return Mono.error(new IllegalStateException("Session not found or closed"));
//        }
//    }
//    private String serializeMessage(MessageObject messageObject) {
//        try {
//            return objectMapper.writeValueAsString(messageObject);
//        } catch (JsonProcessingException e) {
//            throw new IllegalArgumentException(messageObject.toString(), e);
//        }
//    }
//}