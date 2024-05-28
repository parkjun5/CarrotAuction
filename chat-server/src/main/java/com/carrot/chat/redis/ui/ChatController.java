package com.carrot.chat.redis.ui;

import com.carrot.chat.support.client.ChatGrpcClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller("/redis")
@Profile("redis-pub-sub")
public class ChatController {

    private final ChatGrpcClient chatGrpcClient;

    public ChatController(ChatGrpcClient chatGrpcClient) {
        this.chatGrpcClient = chatGrpcClient;
    }

    @GetMapping("/chat/{chatRoomId}/userId/{userId}")
    public String getChatRoom(@PathVariable Long chatRoomId,
                              @PathVariable Long userId,
                              Model model
    ) {
        List<MessageObject> messageObjects = chatGrpcClient.findChatRoomHistoriesById(chatRoomId);
        model.addAttribute("messageObjects", messageObjects);
        model.addAttribute("chatRoomId", chatRoomId);
        model.addAttribute("userId", userId);
        return "chat-index-pub";
    }

}