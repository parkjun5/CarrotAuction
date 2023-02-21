package com.carrot.auction.domain.chat.domain;

import com.carrot.auction.domain.account.domain.entity.Account;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class ChatMessage {

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account sender;


    /**
     * 메시지 생성
     */

    public static ChatMessage of(Long senderId, String chatMessage, ChatRoom chatRoom) {
        //TODO : 전달자 아이디로 전달자 찾고 연결
        //        Account senderId = AccountService.findById(senderId);
        return ChatMessage.builder()
                .sender(null)
                .message(chatMessage)
                .chatRoom(chatRoom)
                .build();
    }
}
