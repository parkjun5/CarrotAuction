package com.carrot.auction.domain.chat.domain;

import com.carrot.auction.domain.user.domain.entity.User;

public interface BaseChatRoom {
    Long sendChat(Long senderId, String message);
    Long readChat(Long readerId);

    default void addParticipants(User user) {
        user.getAuctionRooms().add(null);
    }
}
