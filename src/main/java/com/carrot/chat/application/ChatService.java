package com.carrot.chat.application;

import com.carrot.chat.domain.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class ChatService {
    public List<ChatMessage> findAll() {
        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        IntStream.range(0, 10).forEach(i -> {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMessage(i + " 번째 신규 메세지");
            chatMessage.setSenderId("발신자");
            chatMessage.setChatRoomId(1L);
            chatMessages.add(chatMessage);
        });

        return chatMessages;
    }

    public ChatMessage findByName(String name) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage("새로운 신규 메세지");
        chatMessage.setSenderId(name);
        chatMessage.setChatRoomId(1L);

        return chatMessage;
    }
}
