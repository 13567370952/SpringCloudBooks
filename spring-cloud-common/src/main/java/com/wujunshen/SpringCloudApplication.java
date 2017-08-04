package com.wujunshen;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2017/8/3 <br>
 * Time:下午6:02 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.wujunshen.dao")
public class SpringCloudApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringCloudApplication.class);

    public static void main(String[] args) {
        LOGGER.info("start execute SpringCloudApplication....\n");
        SpringApplication.run(SpringCloudApplication.class, args);
        LOGGER.info("end execute SpringCloudApplication....\n");
    }
}