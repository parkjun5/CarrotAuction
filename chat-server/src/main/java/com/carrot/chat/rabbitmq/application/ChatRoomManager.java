package com.carrot.chat.rabbitmq.application;

import com.carrot.chat.support.client.AuctionGrpcClient;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ChatRoomManager {

    private final AuctionGrpcClient auctionGrpcClient;

    public ChatRoomManager(AuctionGrpcClient auctionGrpcClient) {
        this.auctionGrpcClient = auctionGrpcClient;
    }

    public Set<Long> getUsersInChatRoom(Long chatRoomId, long sederId) {
        var response = auctionGrpcClient.findAllNotActiveUserInChatRoom(chatRoomId, sederId);
        return new HashSet<>(response.getWriterIdList());
    }
}