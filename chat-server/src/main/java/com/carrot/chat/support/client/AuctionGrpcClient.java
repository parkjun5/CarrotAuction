package com.carrot.chat.support.client;

import auctions.AuctionItemServiceGrpc;
import auctions.Auctions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuctionGrpcClient {

    private final AuctionItemServiceGrpc.AuctionItemServiceBlockingStub blockingStub;

    public AuctionGrpcClient(AuctionItemServiceGrpc.AuctionItemServiceBlockingStub blockingStub) {
        this.blockingStub = blockingStub;
    }

    public Auctions.AuctionResponse findAuctionInfoById(long auctionRoomId) {
        var auctionRequest = Auctions.AuctionRequest.newBuilder()
                .setAuctionRoomId(auctionRoomId)
                .build();

        return blockingStub.getAuctionInfo(auctionRequest);
    }

}
