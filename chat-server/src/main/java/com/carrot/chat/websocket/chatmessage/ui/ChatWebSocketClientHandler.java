package com.carrot.chat.websocket.chatmessage.ui;

import com.carrot.chat.websocket.chatmessage.application.ChatMessageMapper;
import com.carrot.chat.websocket.chatmessage.application.MessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ChatWebSocketClientHandler implements WebSocketHandler {
    private final Flux<Object> messageContainer;
    private final MessagePublisher messagePublisher;
    private final ChatMessageMapper chatMessageMapper;

    public ChatWebSocketClientHandler(Flux<Object> messageContainer, MessagePublisher messagePublisher,
                                      ChatMessageMapper chatMessageMapper) {
        this.messageContainer = messageContainer;
        this.messagePublisher = messagePublisher;
        this.chatMessageMapper = chatMessageMapper;

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
        messagePublisher.newUser(session);
        String sessionId = session.getId();

        var otherUserMessage = messageContainer.map(it -> chatMessageMapper.toMessage(it.toString()))
                .filter(it -> !it.sessionId().equals(sessionId))
                .map(it -> chatMessageMapper.serializeAndAddTo(it, session));


        var writtenMessage = session.receive()
                .map(socketMessage -> {
                    String payload = socketMessage.getPayloadAsText();
                    return chatMessageMapper.toMessageWith(payload, sessionId);
                })
                .doOnNext(it -> messagePublisher.sendChatMessage(chatMessageMapper.serialize(it), it))
                .map(it -> chatMessageMapper.serializeAndAddTo(it, session));

        return session.send(Flux.merge(otherUserMessage, writtenMessage))
                .doOnTerminate(() -> messagePublisher.terminateMessage(session))
                .doOnError(error -> log.error("WebSocket error: ", error));
    }
}

