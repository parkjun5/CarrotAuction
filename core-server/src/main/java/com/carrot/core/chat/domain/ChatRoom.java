package com.carrot.core.chat.domain;

import com.carrot.core.chat.application.dto.ChatRoomRequest;
import com.carrot.core.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "chat_room_id")
    private Long id;
    private String name;
    public static ChatRoom of(ChatRoomRequest request) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.name = request.name();
        return chatRoom;
    }

    public void changeName(String name) {
        this.name = name;
    }
}
