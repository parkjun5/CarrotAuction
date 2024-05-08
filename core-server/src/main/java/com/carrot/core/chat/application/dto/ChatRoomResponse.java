package com.carrot.core.chat.application.dto;

import com.carrot.core.chat.domain.ChatRoom;
import lombok.Builder;

@Builder
public record ChatRoomResponse(
        Long chatRoomId,
        String name
) {
    public static ChatRoomResponse from(ChatRoom chatRoom) {
        return new ChatRoomResponse(chatRoom.getId(), chatRoom.getName());
    }

}
