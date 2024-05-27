package com.carrot.chat.redis.application;

import com.carrot.chat.support.client.ChatGrpcClient;
import com.carrot.chat.support.client.UsersGrpcClient;
import com.carrot.chat.redis.ui.MessageObject;
import com.carrot.chat.redis.application.converter.ChannelConverter;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import users.Users;

@Service
@Profile("redis-pub-sub")
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
        chatGrpcClient.recordChatHistory(messageObject.message(), messageObject.userId(), messageObject.chatRoomId());
        String channel = ChannelConverter.channelOf(messageObject.chatRoomId());
        redisTemplate.convertAndSend(channel, messageObject);
    }

    public String getWriterNameById(Long userId) {
        Users.UserNameResponse response = usersGrpcClient.findWriterById(userId);
        return response.getWriter();
    }
}