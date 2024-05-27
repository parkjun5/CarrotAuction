package com.carrot.chat.redis.application;

import com.carrot.chat.redis.ui.MessageObject;
import com.carrot.chat.support.config.ValueObject;
import com.carrot.chat.redis.application.converter.MessageConverter;
import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.logging.Logger;

@Profile("redis-pub-sub")
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
        if (name.equals(messageObject.writer())) {
            return;
        }
        String text = String.format("to: %s => message: %s", name, messageObject);

        log.info(text);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Subscriber that = (Subscriber) object;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
