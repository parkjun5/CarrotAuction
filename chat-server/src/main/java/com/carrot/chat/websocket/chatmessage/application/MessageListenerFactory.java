package com.carrot.chat.websocket.chatmessage.application;

import com.carrot.chat.redis.application.Subscriber;
import com.carrot.chat.redis.config.CustomMessageListenerAdapter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("redis-pub-sub")
public class MessageListenerFactory {

    public CustomMessageListenerAdapter create(String name) {
        return new CustomMessageListenerAdapter(new Subscriber(name));
    }
}
