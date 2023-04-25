package com.carrot.chat.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatMessage {

    @Id
    private Long id;

    private String message;

    private String senderId;

    private String chatRoomId;
}
