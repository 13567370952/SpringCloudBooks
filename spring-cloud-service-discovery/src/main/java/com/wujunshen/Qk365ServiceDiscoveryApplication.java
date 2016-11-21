package com.wujunshen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Qk365ServiceDiscoveryApplication {
    public static void main(String[] args) {
        SpringApplication.run(Qk365ServiceDiscoveryApplication.class, args);
    }
}
