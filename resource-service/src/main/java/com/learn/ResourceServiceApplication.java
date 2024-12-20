package com.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.learn")
public class ResourceServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(ResourceServiceApplication.class, args);
    }
}
