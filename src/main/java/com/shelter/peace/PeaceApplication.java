package com.shelter.peace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PeaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PeaceApplication.class, args);
    }

}
