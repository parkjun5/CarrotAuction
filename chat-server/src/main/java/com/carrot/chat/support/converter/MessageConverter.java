package com.carrot.chat.support.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;

public class MessageConverter {
    private MessageConverter() {

    }

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T convertBody(Message message, Class<T> clazz) {
        T object;

        try {
            object = mapper.readValue(message.getBody(), clazz);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        assert object != null;
        return object;
    }

}
