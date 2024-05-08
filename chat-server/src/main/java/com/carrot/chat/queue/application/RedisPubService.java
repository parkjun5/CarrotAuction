package com.carrot.chat.queue.application;

import com.carrot.chat.queue.ui.ChatGrpcClient;
import com.carrot.chat.queue.ui.MessageObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisPubService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChatGrpcClient chatGrpcClient;
    public RedisPubService(RedisTemplate<String, Object> redisTemplate, ChatGrpcClient chatGrpcClient) {
        this.redisTemplate = redisTemplate;
        this.chatGrpcClient = chatGrpcClient;
    }

    public void sendMessage(MessageObject messageObject) {
        chatGrpcClient.recordChatHistory(messageObject);
        String channel = ChannelConverter.channelOf(messageObject.chatRoomId());
        redisTemplate.convertAndSend(channel, messageObject);
    }

}