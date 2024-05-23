package com.carrot.chat.queue.application;

import com.carrot.chat.queue.application.grpc.ChatGrpcClient;
import com.carrot.chat.queue.application.grpc.UsersGrpcClient;
import com.carrot.chat.queue.ui.MessageObject;
import com.carrot.chat.support.converter.ChannelConverter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import users.Users;

@Service
public class RedisPubService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChatGrpcClient chatGrpcClient;
    private final UsersGrpcClient usersGrpcClient;
    public RedisPubService(RedisTemplate<String, Object> redisTemplate,
                           ChatGrpcClient chatGrpcClient,
                           UsersGrpcClient usersGrpcClient
    ) {
        this.redisTemplate = redisTemplate;
        this.chatGrpcClient = chatGrpcClient;
        this.usersGrpcClient = usersGrpcClient;
    }

    public void sendMessage(MessageObject messageObject) {
        chatGrpcClient.recordChatHistory(messageObject);
        String channel = ChannelConverter.channelOf(messageObject.chatRoomId());
        redisTemplate.convertAndSend(channel, messageObject);
    }

    public String getWriterNameById(Long userId) {
        Users.UserNameResponse response = usersGrpcClient.findWriterById(userId);
        return response.getWriter();
    }
}