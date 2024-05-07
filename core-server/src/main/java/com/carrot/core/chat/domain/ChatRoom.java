package com.carrot.core.chat.domain;

import com.carrot.core.common.domain.BaseEntity;
import com.carrot.core.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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
    @OneToMany(mappedBy = "chatRoom",cascade = CascadeType.ALL)
    @Builder.Default
    private Set<ChatRoomParticipation> chatRoomParticipation = new HashSet<>();

    public void setChatRoomParticipation(User user) {
        this.chatRoomParticipation.add(ChatRoomParticipation.createChatRoomParticipation(user, this));
    }

    public void changeName(String name) {
        this.name = name;
    }
}
