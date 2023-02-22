package com.carrot.auction.domain.chat.domain;

public interface BaseChatRoom {
    Long sendChat(Long senderId, String message);
    Long readChat(Long readerId);
}
