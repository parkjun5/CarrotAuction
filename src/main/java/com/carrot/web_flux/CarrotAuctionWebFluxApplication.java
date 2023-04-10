package com.carrot.web_flux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Profile("web-flux")
@EnableJpaAuditing
@SpringBootApplication
public class CarrotAuctionWebFluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarrotAuctionWebFluxApplication.class, args);
    }

}
