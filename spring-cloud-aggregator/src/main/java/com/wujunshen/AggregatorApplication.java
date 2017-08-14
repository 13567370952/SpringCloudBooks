package com.wujunshen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableTurbine
@Slf4j
public class AggregatorApplication {
    public static void main(String[] args) {
        log.info("start execute AggregatorApplication....\n");
        SpringApplication.run(AggregatorApplication.class, args);
        log.info("end execute AggregatorApplication....\n");
    }
}