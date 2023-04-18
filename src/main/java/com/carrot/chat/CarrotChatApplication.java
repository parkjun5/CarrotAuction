package com.carrot.chat;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.carrot.chat"})
public class CarrotChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarrotChatApplication.class, args);
    }

}
