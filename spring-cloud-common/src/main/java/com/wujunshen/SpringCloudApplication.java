package com.wujunshen;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
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
@Slf4j
public class SpringCloudApplication {
    public static void main(String[] args) {
        log.info("start execute SpringCloudApplication....\n");
        SpringApplication.run(SpringCloudApplication.class, args);
        log.info("end execute SpringCloudApplication....\n");
    }
}