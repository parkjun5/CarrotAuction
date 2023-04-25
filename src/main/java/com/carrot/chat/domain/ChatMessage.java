package com.carrot.chat.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("chatMessage")
public class ChatMessage {

    @Id
    private Long id;

    private String message;

    private String senderId;

    private String chatRoomId;
}
