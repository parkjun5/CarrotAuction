package com.carrot.chat.rabbitmq.ui;

import auctions.Auctions;
import com.carrot.chat.rabbitmq.application.BidManager;
import com.carrot.chat.rabbitmq.application.ChatMessagePostMan;
import com.carrot.chat.rabbitmq.application.DynamicQueueManager;
import com.carrot.chat.redis.ui.MessageObject;
import com.carrot.chat.support.client.AuctionGrpcClient;
import com.carrot.chat.support.client.ChatGrpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ChatController {

    @Value("${message.topic.exchange.name}")
    private String topicExchangeName;

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessagePostMan chatMessagePostMan;
    private final ChatGrpcClient chatGrpcClient;
    private final AuctionGrpcClient auctionGrpcClient;
    private final DynamicQueueManager dynamicQueueManager;
    private final BidManager bidManager;

    public ChatController(SimpMessagingTemplate messagingTemplate, ChatMessagePostMan chatMessagePostMan,
                          ChatGrpcClient chatGrpcClient, AuctionGrpcClient auctionGrpcClient, DynamicQueueManager dynamicQueueManager,
                          BidManager bidManager) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessagePostMan = chatMessagePostMan;
        this.chatGrpcClient = chatGrpcClient;
        this.auctionGrpcClient = auctionGrpcClient;
        this.dynamicQueueManager = dynamicQueueManager;
        this.bidManager = bidManager;
    }

    @GetMapping("/chat/{chatRoomId}/userId/{userId}")
    public String chatRoom(@PathVariable Long chatRoomId,
                           @PathVariable Long userId,
                           Model model) {
        List<MessageObject> messageObjects = chatGrpcClient.findChatRoomHistoriesById(chatRoomId);
        Auctions.AuctionResponse auctionInfoById = auctionGrpcClient.findAuctionInfoById(chatRoomId);
        model.addAttribute("auctionItem", auctionInfoById);
        model.addAttribute("messageObjects", messageObjects);
        model.addAttribute("chatRoomId", chatRoomId);
        model.addAttribute("userId", userId);
        // 원래는 채팅방 가입할 때에만 추가
        dynamicQueueManager.createQueueForUser(topicExchangeName, userId);
        return "chat-room-rabbit";
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatPayload chat) {
        // 채팅 메시지 저장
        ChatPayload chatPayload = chatMessagePostMan.saveChatMessage(chat);

        // 실시간으로 연결된 유저에게 WebSocket을 통해 메시지 전달
        messagingTemplate.convertAndSend("/topic/chatRoom/" + chatPayload.chatRoomId(), chatPayload);

        // RabbitMQ를 통해 메시지 알림 전송
        dynamicQueueManager.sendMessageToChatRoom(topicExchangeName, chatPayload.chatRoomId(),
                chatPayload.userId(), chatPayload.message());
    }

    @MessageMapping("/bid.placeBid")
    public void placeBid(@Payload BidPayload bid) {
        // 입찰 정보 처리
        BidPayload bidPayload = bidManager.processBid(bid);

        // 실시간으로 연결된 유저에게 WebSocket을 통해 메시지 전달
        messagingTemplate.convertAndSend("/topic/bid/" + bidPayload.chatRoomId(), bidPayload);

        // RabbitMQ를 통해 메시지 알림 전송
        dynamicQueueManager.sendMessageToChatRoom(topicExchangeName, bidPayload.chatRoomId(),
                bidPayload.bidderId(), "새로운 입찰이 있습니다!");
    }
}