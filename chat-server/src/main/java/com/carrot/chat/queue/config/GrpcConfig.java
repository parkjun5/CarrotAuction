package com.carrot.chat.queue.config;


import chat.ChatHistoryRecorderGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {
    @Bean
    public ManagedChannel managedChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 50001)
                .usePlaintext()
                .build();
    }

    @Bean
    public ChatHistoryRecorderGrpc.ChatHistoryRecorderBlockingStub chatStub(ManagedChannel managedChannel) {
        return ChatHistoryRecorderGrpc.newBlockingStub(managedChannel);
    }

}
