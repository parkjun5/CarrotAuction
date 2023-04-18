package com.carrot.chat.config;


import com.carrot.chat.application.ChatHandlerT;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@Configuration
@EnableWebFlux
@EnableWebSocket
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, SpringApplicationAdminJmxAutoConfiguration.class, })
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(ChatHandlerT chatHandler) {
        return RouterFunctions
                //CRUD
                // 채팅 방만들기/ 조회 / 수정
                .route()
                .GET("/user/{name}", chatHandler::findByName)
                .GET("/user", chatHandler::findAll)
                .build();
    }



}