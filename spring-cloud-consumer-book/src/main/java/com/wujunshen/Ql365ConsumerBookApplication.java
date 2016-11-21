package com.wujunshen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class Ql365ConsumerBookApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(Ql365ConsumerBookApplication.class);

    public static void main(String[] args) {
        LOGGER.info("start execute Ql365ConsumerBookApplication....\n");
        SpringApplication.run(Ql365ConsumerBookApplication.class, args);
        LOGGER.info("end execute Ql365ConsumerBookApplication....\n");
    }
}