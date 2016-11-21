package com.wujunshen;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.wujunshen.dao")
@EnableDiscoveryClient
public class Qk365ProviderBookApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(Qk365ProviderBookApplication.class);

    public static void main(String[] args) {
        LOGGER.info("start execute Qk365ProviderBookApplication....\n");
        SpringApplication.run(Qk365ProviderBookApplication.class, args);
        LOGGER.info("end execute Qk365ProviderBookApplication....\n");
    }
}