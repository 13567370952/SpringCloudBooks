package com.wujunshen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

@SpringBootApplication
@EnableZipkinStreamServer
@Slf4j
public class ZipkinServerApplication {
    public static void main(String[] args) {
        log.info("start execute ZipkinServerApplication....\n");
        SpringApplication.run(ZipkinServerApplication.class, args);
        log.info("end execute ZipkinServerApplication....\n");
    }
}