package com.carrot.parkjun5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CarrotAuctionApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarrotAuctionApplication.class, args);
    }

}
