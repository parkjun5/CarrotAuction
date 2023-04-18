package com.carrot.chat.config;


import com.carrot.chat.application.RouteHandler;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@EnableWebFlux
@EnableAutoConfiguration(exclude={ DataSourceAutoConfiguration.class, SpringApplicationAdminJmxAutoConfiguration.class })
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(RouteHandler routeHandler) {
        //CRUD
        return RouterFunctions
                .route()
                .GET("/chat/room/{roomId}", routeHandler::findByName)
                .GET("/chat/room", routeHandler::findAll)
                .build();
    }



}