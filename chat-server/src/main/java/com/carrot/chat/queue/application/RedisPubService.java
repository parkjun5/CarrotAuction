package com.carrot.chat.queue.application;

import com.carrot.chat.queue.ui.MessageObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisPubService {
    private final RedisTemplate<String, Object> redisTemplate;
    public RedisPubService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void sendMessage(MessageObject messageObject) {
        String channel = ChannelConverter.channelOf(messageObject.chatRoomId());
        redisTemplate.convertAndSend(channel, messageObject);
    }

}