package com.carrot.auction.domain.chat.domain;

public class PrivateChatRoom implements ChatRoom{
    @Override
    public Long sendChat(Long senderId, String message) {
        return null;
    }

    @Override
    public Long readChat(Long readerId) {
        return null;
    }
}
