package com.carrot.chat.websocket.chatmessage.config;

import com.carrot.chat.websocket.chatmessage.ui.ChatWebSocketClientHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSocket
@Profile("redis-pub-sub")
public class WebSocketClientConfig {
    @Bean
    public HandlerMapping handlerMapping(ChatWebSocketClientHandler handler) {
        Map<String, WebSocketHandler> handlerByPathMap = new HashMap<>();
        handlerByPathMap.put("/chat/message", handler);
        return new SimpleUrlHandlerMapping(handlerByPathMap, -1);
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    public Sinks.Many<Object> sinks() {
        return Sinks.many().multicast().onBackpressureBuffer(1000);
    }

    @Bean
    public Flux<Object> messageContainer(Sinks.Many<Object> sinks) {
        return sinks.asFlux().onBackpressureBuffer(1000, BufferOverflowStrategy.DROP_OLDEST);
    }

}
