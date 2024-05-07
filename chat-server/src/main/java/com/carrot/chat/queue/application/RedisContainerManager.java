package com.carrot.chat.queue.application;

import com.carrot.chat.queue.config.CustomMessageListenerAdapter;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@Service
public class RedisContainerManager {

    private final RedisMessageListenerContainer redisContainer;

    public RedisContainerManager(RedisMessageListenerContainer redisContainer) {
        this.redisContainer = redisContainer;
    }

    public void addSub(Long chatRoomId, String name) {
        CustomMessageListenerAdapter messageListenerAdapter = makeSubscriberMessageListenerAdapter(name);
        String channel = ChannelConverter.channelOf(chatRoomId);
        redisContainer.addMessageListener(messageListenerAdapter, new ChannelTopic(channel));
    }

    public void removeSub(Long chatRoomId, String name) {
        CustomMessageListenerAdapter messageListenerAdapter = makeSubscriberMessageListenerAdapter(name);
        String channel = ChannelConverter.channelOf(chatRoomId);
        redisContainer.removeMessageListener(messageListenerAdapter, new ChannelTopic(channel));
    }

    private CustomMessageListenerAdapter makeSubscriberMessageListenerAdapter(String name) {
        return new CustomMessageListenerAdapter(new Subscriber(name));
    }

}
