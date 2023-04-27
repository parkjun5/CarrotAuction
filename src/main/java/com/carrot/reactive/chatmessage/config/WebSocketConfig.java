package com.carrot.reactive.chatmessage.config;

import com.carrot.reactive.chatmessage.application.ChatWebSocketHandler;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class WebSocketConfig {
    @Bean
    public HandlerMapping handlerMapping(ChatWebSocketHandler handler) {
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
        return  Sinks.many().replay().limit(3);
    }

    @Bean
    public Flux<Object> chatMessages(Sinks.Many<Object> sinks) {
        return sinks.asFlux().onBackpressureBuffer(3, BufferOverflowStrategy.DROP_OLDEST);
    }


}
