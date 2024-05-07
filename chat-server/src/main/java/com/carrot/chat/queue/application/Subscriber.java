package com.carrot.chat.queue.application;

import com.carrot.chat.queue.ui.MessageObject;
import com.carrot.chat.support.config.ValueObject;
import jakarta.annotation.Nonnull;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.util.StringUtils;

import java.util.logging.Logger;

public class Subscriber extends ValueObject<Subscriber> implements MessageListener {

    private static final Logger log = Logger.getLogger(Subscriber.class.getName());
    private final String name;

    public Subscriber(String name) {

        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("name should not null");
        }

        this.name = name;
    }

    @Override
    public void onMessage(@Nonnull Message message, byte[] pattern) {

        MessageObject messageObject = MessageConverter.convertBody(message, MessageObject.class);
        if (name.equals(messageObject.name())) {
            return;
        }
        String text = String.format("to: %s => message: %s", name, messageObject);

        log.info(text);
    }

}
