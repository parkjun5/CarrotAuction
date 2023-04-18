package com.carrot.chat.config;


import com.carrot.chat.application.ChatHandler;
import com.carrot.chat.application.ChatHandlerT;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
@Configuration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, SpringApplicationAdminJmxAutoConfiguration.class})
@EnableWebSocket
public class WebSocketConfiguration  {

    @Bean
    public RouterFunction<ServerResponse> routes(ChatHandlerT chatHandler) {
        return RouterFunctions
                .route(GET("/user/{name}").and(accept(MediaType.APPLICATION_JSON)), chatHandler::findByName)
                .andRoute(GET("/user").and(accept(MediaType.APPLICATION_JSON)), chatHandler::findAll);
    }


    @Bean
    public ChatHandler chatHandler() {
        return new ChatHandler();
    }

    @Bean
    public HandlerMapping handlerMapping(ChatHandler chatHandler) {
        Map<String, WebSocketHandler> handlerByPathMap = new HashMap<>();
        handlerByPathMap.put("/chat", chatHandler);

        return new SimpleUrlHandlerMapping(handlerByPathMap, -1);
    }

}