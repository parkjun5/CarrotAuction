package com.carrot.chat.rabbitmq.ui;


import chat.Chat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public record ChatPayload(
        String type,
        String message,
        LocalDateTime timestamp,
        Long chatRoomId,
        String writer,
        Long userId
) {
    public static ChatPayload from(Chat.ChatRecord chatRecord, long chatRoomId) {
        Instant instant = Instant.ofEpochSecond(chatRecord.getSendAt().getSeconds(), chatRecord.getSendAt().getNanos());
        return new ChatPayload(
                "CHAT",
                chatRecord.getMessage(),
                LocalDateTime.ofInstant(instant, ZoneId.systemDefault()),
                chatRoomId,
                chatRecord.getWriter(),
                chatRecord.getWriterId()
        );
    }

    public ChatPayload setWriter(String writerName) {
        return new ChatPayload(
                this.type,
                this.message,
                LocalDateTime.now(),
                this.chatRoomId,
                writerName,
                this.userId
        );
    }
}
