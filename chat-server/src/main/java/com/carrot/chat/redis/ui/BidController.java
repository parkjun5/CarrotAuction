package com.carrot.chat.redis.ui;

import auctions.Auctions;
import com.carrot.chat.support.client.AuctionGrpcClient;
import com.carrot.chat.support.client.ChatGrpcClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("/redis")
@Profile("redis-pub-sub")
public class BidController {
    private final ChatGrpcClient chatGrpcClient;
    private final AuctionGrpcClient auctionGrpcClient;

    public BidController(ChatGrpcClient chatGrpcClient, AuctionGrpcClient auctionGrpcClient) {
        this.chatGrpcClient = chatGrpcClient;
        this.auctionGrpcClient = auctionGrpcClient;
    }

    @GetMapping("/bidRoom/{chatRoomId}/userId/{userId}")
    public String enterBidRoom(@PathVariable Long chatRoomId,
                              @PathVariable Long userId,
                              Model model
    ) {
        List<MessageObject> messageObjects = chatGrpcClient.findChatRoomHistoriesById(chatRoomId);
        Auctions.AuctionResponse auctionInfoById = auctionGrpcClient.findAuctionInfoById(chatRoomId);
        model.addAttribute("auctionItem", auctionInfoById);
        model.addAttribute("messageObjects", messageObjects);
        model.addAttribute("chatRoomId", chatRoomId);
        model.addAttribute("userId", userId);
        return "bid-index";
    }
}
