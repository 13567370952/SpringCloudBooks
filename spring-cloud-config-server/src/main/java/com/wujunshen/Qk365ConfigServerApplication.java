package com.wujunshen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class Qk365ConfigServerApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(Qk365ConfigServerApplication.class);

    public static void main(String[] args) {
        LOGGER.info("start execute Qk365ConfigServerApplication....\n");
        SpringApplication.run(Qk365ConfigServerApplication.class, args);
        LOGGER.info("end execute Qk365ConfigServerApplication....\n");
    }
}