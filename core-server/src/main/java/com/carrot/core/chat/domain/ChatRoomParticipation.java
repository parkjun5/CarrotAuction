package com.carrot.core.chat.domain;

import com.carrot.core.common.domain.BaseEntity;
import com.carrot.core.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomParticipation extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "chat_room_participation_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    /**
     * 연관 관계 메소드
     */

    public void setUser(User user) {
        this.user = user;
        user.getParticipatedChatRoom().add(this);
    }

    private void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public static ChatRoomParticipation createChatRoomParticipation(User user, ChatRoom chatRoom) {
        ChatRoomParticipation auctionParticipation = new ChatRoomParticipation();
        auctionParticipation.setUser(user);
        auctionParticipation.setChatRoom(chatRoom);
        return auctionParticipation;
    }

}
