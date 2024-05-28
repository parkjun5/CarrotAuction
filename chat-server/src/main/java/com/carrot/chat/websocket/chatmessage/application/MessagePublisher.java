package com.carrot.chat.websocket.chatmessage.application;

import com.carrot.chat.redis.application.RedisContainerManager;
import com.carrot.chat.redis.application.RedisPubService;
import com.carrot.chat.redis.ui.MessageObject;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Sinks;

import java.net.URI;

@Service
@Profile("redis-pub-sub")
public class MessagePublisher {

    public static final String WELCOME_MESSAGE = "님이 채팅방에 입장하였습니다.";
    public static final String GOODBYE = "님이 채팅방에서 나갔습니다!";

    private final RedisContainerManager redisContainerManager;
    private final ChatMessageMapper chatMessageMapper;
    private final RedisPubService redisPubService;
    private final Sinks.Many<Object> sinks;

    public MessagePublisher(RedisContainerManager containerManager, ChatMessageMapper chatMessageMapper,
                            RedisPubService redisPubService, Sinks.Many<Object> sinks) {
        this.redisContainerManager = containerManager;
        this.chatMessageMapper = chatMessageMapper;
        this.redisPubService = redisPubService;
        this.sinks = sinks;
    }

    public void newUser(WebSocketSession session) {
        if (session.isOpen()) {
            URI uri = session.getHandshakeInfo().getUri();
            String newUser = redisContainerManager.addSubscriber(uri);
            String message = chatMessageMapper.serialize(newUser, WELCOME_MESSAGE);
            sinks.emitNext(message, Sinks.EmitFailureHandler.FAIL_FAST);
        }
    }

    public void terminateMessage(WebSocketSession session) {
        URI uri = session.getHandshakeInfo().getUri();
        String newUser = redisContainerManager.removeSubscriber(uri);
        String message = chatMessageMapper.serialize(newUser, GOODBYE);
        sinks.emitNext(message, Sinks.EmitFailureHandler.FAIL_FAST);
    }

    public void sendChatMessage(String payload, MessageObject messageObject) {
        sinks.tryEmitNext(payload);
        redisPubService.sendMessage(messageObject);
    }
}
