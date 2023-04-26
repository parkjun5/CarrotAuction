package com.carrot.chat;


import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication(scanBasePackages = {"com.carrot.chat"})
public class CarrotChatApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(CarrotChatApplication.class)
                .profiles("chat")
                .web(WebApplicationType.REACTIVE)
                .run(args);
    }
}
