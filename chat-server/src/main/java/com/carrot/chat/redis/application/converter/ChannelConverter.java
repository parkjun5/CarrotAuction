package com.carrot.chat.redis.application.converter;


import org.springframework.context.annotation.Profile;

import java.nio.ByteBuffer;
import java.util.UUID;

@Profile("redis-pub-sub")
public class ChannelConverter {

    private ChannelConverter() {

    }

    public static String channelOf(Long chatRoomId) {
        if (chatRoomId == null) {
            throw new IllegalArgumentException("chatRoomId must not null");
        }

        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(chatRoomId);
        UUID uuid = UUID.nameUUIDFromBytes(buffer.array());

        return uuid.toString();
    }
}
