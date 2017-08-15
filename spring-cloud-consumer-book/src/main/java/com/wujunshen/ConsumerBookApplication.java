package com.wujunshen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@Slf4j
@ServletComponentScan
public class ConsumerBookApplication {
    public static void main(String[] args) {
        log.info("start execute ConsumerBookApplication....\n");
        SpringApplication.run(ConsumerBookApplication.class, args);
        log.info("end execute ConsumerBookApplication....\n");
    }
}