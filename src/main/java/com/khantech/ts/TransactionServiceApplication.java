package com.khantech.ts;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TransactionServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(TransactionServiceApplication.class, args);
    }
}
