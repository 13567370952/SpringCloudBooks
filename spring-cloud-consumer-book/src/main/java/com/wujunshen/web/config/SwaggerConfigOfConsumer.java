package com.wujunshen.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2017/8/8 <br>
 * Time:下午6:00 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@Configuration
@EnableSwagger2
public class SwaggerConfigOfConsumer extends SwaggerConfig {
    @Override
    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("consumer")
                .apiInfo(apiInfo())
                .select()
                .paths(regex("/consumer/.*"))
                .build();
    }
}