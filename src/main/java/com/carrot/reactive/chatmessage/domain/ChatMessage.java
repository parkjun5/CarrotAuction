package com.carrot.reactive.chatmessage.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@Getter @Setter
@Document(collection = "chat_message")
public class ChatMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 2868210232929931052L;
    @Transient
    public static final String SEQUENCE_NAME = "chat_message_sequence";
    @Id
    private Long id;
    private String message;
    private String senderId;
    private Long chatRoomId;
}
