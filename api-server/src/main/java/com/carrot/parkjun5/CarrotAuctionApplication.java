package com.carrot.parkjun5;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CarrotAuctionApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarrotAuctionApplication.class, args);
    }

}
