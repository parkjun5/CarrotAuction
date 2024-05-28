package com.carrot.chat.rabbitmq.application;

import com.carrot.chat.rabbitmq.ui.BidPayload;
import com.carrot.chat.support.client.AuctionGrpcClient;
import com.carrot.chat.support.client.UsersGrpcClient;
import org.springframework.stereotype.Service;
import users.Users;

@Service
public class BidManager {

    private final UsersGrpcClient usersGrpcClient;
    private final AuctionGrpcClient auctionGrpcClient;


    public BidManager(UsersGrpcClient usersGrpcClient, AuctionGrpcClient auctionGrpcClient) {
        this.usersGrpcClient = usersGrpcClient;
        this.auctionGrpcClient = auctionGrpcClient;
    }

    public BidPayload processBid(BidPayload bid) {
        Users.UserNameResponse response = usersGrpcClient.findWriterById(bid.bidderId());

        BidPayload bidPayload = bid.setWriter(response.getWriter());

        auctionGrpcClient.bidAuction(bidPayload.auctionId(), bidPayload.bidAmount(),
                                     bidPayload.bidderId());
        return bidPayload;
    }
}
