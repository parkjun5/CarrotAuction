package com.carrot.chat.queue.config;

import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.Objects;

public class CustomMessageListenerAdapter extends MessageListenerAdapter {

    public CustomMessageListenerAdapter(Object delegate) {
        super(delegate);
    }

    @Override
    public int hashCode() {
        return Objects.requireNonNull(super.getDelegate()).hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other instanceof CustomMessageListenerAdapter adapter) {
            return Objects.equals(super.getDelegate(), adapter.getDelegate());
        }

        return false;
    }
}
