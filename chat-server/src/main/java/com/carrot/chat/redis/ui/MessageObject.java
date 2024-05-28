package com.carrot.chat.redis.ui;

import chat.Chat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public record MessageObject(
        String type,
        String message,
        LocalDateTime timestamp,
        Long chatRoomId,
        String writer,
        String sessionId,
        Long userId
) {
    public static MessageObject from(Chat.ChatRecord chatRecord, long chatRoomId) {
        Instant instant = Instant.ofEpochSecond(chatRecord.getSendAt().getSeconds(), chatRecord.getSendAt().getNanos());
        return new MessageObject(
                "CHAT",
                chatRecord.getMessage(),
                LocalDateTime.ofInstant(instant, ZoneId.systemDefault()),
                chatRoomId,
                chatRecord.getWriter(),
                "-1",
                chatRecord.getWriterId()
        );
    }

    public MessageObject changeInfo(String writerName, String sessionId) {
        return new MessageObject(
                this.type,
                this.message,
                LocalDateTime.now(),
                this.chatRoomId,
                writerName,
                sessionId,
                this.userId
        );
    }
    public MessageObject changeWriter(String writerName) {
        return new MessageObject(
                this.type,
                this.message,
                LocalDateTime.now(),
                this.chatRoomId,
                writerName,
                sessionId,
                this.userId
        );
    }
}
