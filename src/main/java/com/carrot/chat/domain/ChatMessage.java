package com.carrot.chat.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter
@Document(collection = "chat_message")
public class ChatMessage {

    @Transient
    public static final String SEQUENCE_NAME = "chat_message_sequence";
    @Id
    private Long id;
    private String message;
    private String senderId;
    private String chatRoomId;
}
