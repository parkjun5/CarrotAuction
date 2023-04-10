package com.carrot.web_mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CarrotAuctionWebMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarrotAuctionWebMvcApplication.class, args);
    }

}
