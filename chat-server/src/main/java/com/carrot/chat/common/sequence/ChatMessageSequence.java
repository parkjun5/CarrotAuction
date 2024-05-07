package com.carrot.chat.common.sequence;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter
@Document("chat_message_sequence")
public class ChatMessageSequence {
    @Id
    private String id;

    private Long seq;
}
