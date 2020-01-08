package com.hp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class LyserachApplication {
    public static void main(String[] args) {
        SpringApplication.run(LyserachApplication.class,args);
    }
}
