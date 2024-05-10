package com.carrot.chat.websocket.chatmessage.application;

import com.carrot.chat.queue.application.Subscriber;
import com.carrot.chat.queue.config.CustomMessageListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class MessageListenerFactory {


    public CustomMessageListenerAdapter create(String name) {
        return new CustomMessageListenerAdapter(new Subscriber(name));
    }
}
