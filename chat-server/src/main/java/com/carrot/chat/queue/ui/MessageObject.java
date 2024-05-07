package com.carrot.chat.queue.ui;

import java.sql.Timestamp;

public record MessageObject(
        String message,
        Timestamp timestamp,
        Long chatRoomId,
        String name,
        Long userId
) {
}
