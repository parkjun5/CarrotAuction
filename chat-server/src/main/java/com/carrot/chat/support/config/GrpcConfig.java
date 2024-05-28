package com.carrot.chat.support.config;


import auctions.AuctionItemServiceGrpc;
import chat.ChatHistoryRecorderGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import users.UserFinderGrpc;

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

    @Bean
    public UserFinderGrpc.UserFinderBlockingStub userStub(ManagedChannel managedChannel) {
        return UserFinderGrpc.newBlockingStub(managedChannel);
    }

    @Bean
    public AuctionItemServiceGrpc.AuctionItemServiceBlockingStub auctionBlockingStub(ManagedChannel managedChannel) {
        return AuctionItemServiceGrpc.newBlockingStub(managedChannel);
    }

}
