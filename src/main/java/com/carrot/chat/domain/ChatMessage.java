package com.carrot.chat.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ChatMessage {

    private String message;

    private String senderId;

    private String chatRoomId;
}
