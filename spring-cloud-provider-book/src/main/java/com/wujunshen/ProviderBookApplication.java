package com.wujunshen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class ProviderBookApplication {
    public static void main(String[] args) {
        log.info("start execute ProviderBookApplication....\n");
        SpringApplication.run(ProviderBookApplication.class, args);
        log.info("end execute ProviderBookApplication....\n");
    }
}