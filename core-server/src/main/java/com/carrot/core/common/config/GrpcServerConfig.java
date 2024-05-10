package com.carrot.core.common.config;

import com.carrot.core.chat.application.ChatHistoryRecorderImpl;
import com.carrot.core.user.application.UserFinderImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;

import java.io.IOException;

@Configuration
public class GrpcServerConfig {

    @Bean(name = "grpcServer")
    public Server grpcServer(ChatHistoryRecorderImpl chatHistoryRecorder,
                             UserFinderImpl userFinder
    ) throws IOException {
        Server server = ServerBuilder.forPort(50001)
            .addService(chatHistoryRecorder)
            .addService(userFinder)
            .build();
        server.start();
        return server;
    }

    @Bean
    public ApplicationListener<ContextClosedEvent> gracefulShutdown(@Qualifier("grpcServer") Server server) {
        return event -> server.shutdown();
    }
}