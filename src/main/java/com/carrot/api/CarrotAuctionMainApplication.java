package com.carrot.api;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = {"com.carrot.api"})
public class CarrotAuctionMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarrotAuctionMainApplication.class, args);
    }

}
