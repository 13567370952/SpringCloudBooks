package com.wujunshen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableTurbine
public class AggregatorApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(AggregatorApplication.class);

    public static void main(String[] args) {
        LOGGER.info("start execute AggregatorApplication....\n");
        SpringApplication.run(AggregatorApplication.class, args);
        LOGGER.info("end execute AggregatorApplication....\n");
    }
}