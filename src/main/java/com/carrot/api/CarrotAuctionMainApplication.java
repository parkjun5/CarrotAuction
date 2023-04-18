package com.carrot.api;


import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = {"com.carrot.api"})
public class CarrotAuctionMainApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CarrotAuctionMainApplication.class)
                .profiles("main-api")
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}
