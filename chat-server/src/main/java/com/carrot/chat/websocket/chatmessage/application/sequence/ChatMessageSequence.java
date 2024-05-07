package com.carrot.chat.websocket.chatmessage.application.sequence;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter
@Profile("mongodb")
@Document("chat_message_sequence")
public class ChatMessageSequence {
    @Id
    private String id;

    private Long seq;
}
