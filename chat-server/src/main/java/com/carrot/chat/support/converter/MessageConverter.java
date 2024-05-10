package com.carrot.chat.support.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;

public class MessageConverter {
    private MessageConverter() {

    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static <T> T convertBody(Message message, Class<T> clazz) {
        T object;

        try {
            object = objectMapper.readValue(message.getBody(), clazz);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        assert object != null;
        return object;
    }

}
