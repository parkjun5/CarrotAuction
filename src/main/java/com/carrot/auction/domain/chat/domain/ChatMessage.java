package com.carrot.auction.domain.chat.domain;

import com.carrot.auction.domain.user.domain.entity.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class ChatMessage implements Message {

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    private BaseChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User sender;


    /**
     * 메시지 생성
     */

    public static ChatMessage of(Long senderId, String chatMessage, BaseChatRoom chatRoom) {
        //TODO : 전달자 아이디로 전달자 찾고 연결
        //        Account senderId = AccountService.findById(senderId);
        return ChatMessage.builder()
                .sender(null)
                .message(chatMessage)
                .chatRoom(chatRoom)
                .build();
    }
}
