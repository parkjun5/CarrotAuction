package com.carrot.core.chat.domain;

import com.carrot.core.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatHistory extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "chat_history_id")
    private Long id;
    private String message;
    private LocalDateTime sendAt;
    private Long writerId;
    private String writer;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    public static ChatHistory of(String message, LocalDateTime sendAt,
                                 long writerId, String writer, ChatRoom chatRoom
    ) {
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.message = message;
        chatHistory.sendAt = sendAt;
        chatHistory.writerId = writerId;
        chatHistory.writer = writer;
        chatHistory.chatRoom = chatRoom;
        return chatHistory;
    }
}
