package com.carrot.reactive;


import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = {"com.carrot.reactive"})
public class CarrotChatApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(CarrotChatApplication.class)
                .profiles("chat")
                .web(WebApplicationType.REACTIVE)
                .run(args);
    }
}
