package com.carrot.chat.queue.application;

import com.carrot.chat.queue.application.grpc.UsersGrpcClient;
import com.carrot.chat.queue.config.CustomMessageListenerAdapter;
import com.carrot.chat.support.converter.ChannelConverter;
import com.carrot.chat.websocket.chatmessage.application.MessageListenerFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class RedisContainerManager {

    private final RedisMessageListenerContainer redisContainer;
    private final UsersGrpcClient usersGrpcClient;
    private final MessageListenerFactory messageListenerFactory;

    public RedisContainerManager(RedisMessageListenerContainer redisContainer, UsersGrpcClient usersGrpcClient,
                                 MessageListenerFactory subscriberFactory) {
        this.redisContainer = redisContainer;
        this.usersGrpcClient = usersGrpcClient;
        this.messageListenerFactory = subscriberFactory;
    }

    public void addSub(Long chatRoomId, String name) {
        if ("관리자".equals(name)) {
            return;
        }
        CustomMessageListenerAdapter messageListenerAdapter = messageListenerFactory.create(name);
        String channel = ChannelConverter.channelOf(chatRoomId);
        redisContainer.addMessageListener(messageListenerAdapter, new ChannelTopic(channel));
    }

    public String addSubscriber(URI uri) {
        SessionParams sessionParams = getSessionParamsBy(uri);
        if (sessionParams == null) {
            return null;
        }

        String name = usersGrpcClient.findWriterById(sessionParams.userId()).getWriter();

        if ("관리자".equals(name)) {
            return name;
        }

        CustomMessageListenerAdapter messageListenerAdapter = messageListenerFactory.create(name);
        String channel = ChannelConverter.channelOf(sessionParams.chatRoomId());
        redisContainer.addMessageListener(messageListenerAdapter, new ChannelTopic(channel));
        return name;
    }

    public void removeSub(Long chatRoomId, String name) {
        CustomMessageListenerAdapter messageListenerAdapter = messageListenerFactory.create(name);
        String channel = ChannelConverter.channelOf(chatRoomId);
        redisContainer.removeMessageListener(messageListenerAdapter, new ChannelTopic(channel));
    }

    public String removeSubscriber(URI uri) {
        SessionParams sessionParams = getSessionParamsBy(uri);
        if (sessionParams == null) {
            return null;
        }

        String name = usersGrpcClient.findWriterById(sessionParams.userId()).getWriter();

        if ("관리자".equals(name)) {
            return name;
        }

        CustomMessageListenerAdapter messageListenerAdapter = messageListenerFactory.create(name);
        String channel = ChannelConverter.channelOf(sessionParams.chatRoomId());
        redisContainer.removeMessageListener(messageListenerAdapter, new ChannelTopic(channel));
        return name;
    }

    private SessionParams getSessionParamsBy(URI uri) {
        List<NameValuePair> pairs = URLEncodedUtils.parse(uri, StandardCharsets.UTF_8);
        long chatRoomId = -1L;
        long userId = -1L;
        for (NameValuePair pair : pairs) {
            String name = pair.getName();
            if ("chatRoomId".equals(name)) {
                chatRoomId = Long.parseLong(pair.getValue());
            } else if ("userId".equals(name)) {
                userId = Long.parseLong(pair.getValue());
            }
        }

        if (chatRoomId == -1L || userId == -1L) {
            return null;
        }

        return new SessionParams(chatRoomId, userId);
    }

    private record SessionParams(long chatRoomId, long userId) {
    }
}
