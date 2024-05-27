package com.carrot.chat.rabbitmq.application;

import com.carrot.chat.rabbitmq.ui.ChatPayload;
import com.carrot.chat.support.client.ChatGrpcClient;
import com.carrot.chat.support.client.UsersGrpcClient;
import org.springframework.stereotype.Service;
import users.Users;

@Service
public class ChatMessagePostMan {

    private final ChatGrpcClient chatGrpcClient;
    private final UsersGrpcClient usersGrpcClient;

    public ChatMessagePostMan(ChatGrpcClient chatGrpcClient, UsersGrpcClient usersGrpcClient) {
        this.chatGrpcClient = chatGrpcClient;
        this.usersGrpcClient = usersGrpcClient;
    }

    public ChatPayload saveChatMessage(ChatPayload chat) {
        Users.UserNameResponse response = usersGrpcClient.findWriterById(chat.userId());

        ChatPayload chatPayload = chat.setWriter(response.getWriter());

        chatGrpcClient.recordChatHistory(chatPayload.message(), chatPayload.userId(), chatPayload.chatRoomId());

        return chatPayload;
    }
}