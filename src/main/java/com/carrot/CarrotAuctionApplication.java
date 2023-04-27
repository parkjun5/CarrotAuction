package com.carrot;


import com.carrot.api.CarrotAuctionMainApplication;
import com.carrot.reactive.CarrotChatApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CarrotAuctionApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CarrotAuctionMainApplication.class)
                .profiles("main-api")
                .web(WebApplicationType.SERVLET)
                .run(args);

        new SpringApplicationBuilder(CarrotChatApplication.class)
                .profiles("chat")
                .web(WebApplicationType.REACTIVE)
                .run(args);
    }

}
