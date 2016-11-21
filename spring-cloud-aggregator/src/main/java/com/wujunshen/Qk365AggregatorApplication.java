package com.wujunshen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableTurbine
public class Qk365AggregatorApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(Qk365AggregatorApplication.class);

    public static void main(String[] args) {
        LOGGER.info("start execute Qk365AggregatorApplication....\n");
        new SpringApplicationBuilder(Qk365AggregatorApplication.class).web(true).run(args);
        LOGGER.info("end execute Qk365AggregatorApplication....\n");
    }
}
