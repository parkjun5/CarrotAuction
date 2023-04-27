package com.carrot.api.chat.domain;

import com.carrot.api.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "chat_room_id")
    private Long id;
    private String name;
    @OneToMany(mappedBy = "chatRoom")
    @Builder.Default
    private Set<ChatRoomParticipation> chatRoomParticipation = new HashSet<>();
}
