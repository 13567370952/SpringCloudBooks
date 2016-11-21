package com.wujunshen;

import com.wujunshen.security.Audience;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.wujunshen.dao")
@EnableDiscoveryClient
@EnableConfigurationProperties(Audience.class)
@EnableZuulProxy
public class Qk365ApiGatewayApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(Qk365ApiGatewayApplication.class);

    public static void main(String[] args) {
        LOGGER.info("start execute Qk365ApiGatewayApplication....\n");
        SpringApplication.run(Qk365ApiGatewayApplication.class, args);
        LOGGER.info("end execute Qk365ApiGatewayApplication....\n");
    }
}