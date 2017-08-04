package com.wujunshen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

@SpringBootApplication
@EnableZipkinStreamServer
public class ZipkinServerApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZipkinServerApplication.class);

    public static void main(String[] args) {
        LOGGER.info("start execute ZipkinServerApplication....\n");
        SpringApplication.run(ZipkinServerApplication.class, args);
        LOGGER.info("end execute ZipkinServerApplication....\n");
    }
}